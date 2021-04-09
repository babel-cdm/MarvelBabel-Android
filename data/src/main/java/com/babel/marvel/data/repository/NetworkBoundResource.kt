package com.babel.marvel.data.repository

import com.babel.marvel.domain.Constants.Companion.CACHE_ERROR_TIMEOUT
import com.babel.marvel.domain.Constants.Companion.CACHE_TIMEOUT
import com.babel.marvel.domain.Constants.Companion.NETWORK_ERROR
import com.babel.marvel.domain.Constants.Companion.NETWORK_ERROR_TIMEOUT
import com.babel.marvel.domain.Constants.Companion.NETWORK_TIMEOUT
import com.babel.marvel.domain.Constants.Companion.UNKNOWN_ERROR
import com.babel.marvel.domain.datastate.DataState
import com.babel.marvel.domain.datastate.MessageType
import com.babel.marvel.domain.datastate.StateMessage
import com.babel.marvel.domain.datastate.UIComponentType
import java.io.IOException
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

abstract class NetworkBoundResource<NetworkObj, CacheObj, ViewState>(
    private val dispatcher: CoroutineDispatcher,
    private val apiCall: (suspend () -> NetworkObj?)? = null,
    val cacheCall: (suspend () -> CacheObj?)? = null
) {
    val result: Flow<DataState<ViewState>> = flow {
        emit(DataState.LOADING(isLoading = true))
        cacheCall?.let {
            emitAll(safeCacheCall())
        }
        apiCall?.let {
            emitAll(safeAPICall())
        }
        emit(DataState.LOADING<ViewState>(isLoading = false))
    }

    private suspend fun safeAPICall() = flow<DataState<ViewState>> {
        withContext(dispatcher) {
            kotlin.runCatching {
                withTimeout(NETWORK_TIMEOUT) {
                    val result = apiCall?.invoke()
                    result?.let {
                        updateCache(result)
                        handleNetworkSuccess(result)?.let {
                            emit(it)
                        }
                    } ?: emit(buildDialogError(UNKNOWN_ERROR))
                }
            }.onFailure {
                it.printStackTrace()
                when (it) {
                    is TimeoutCancellationException -> {
                        emit(buildDialogError(NETWORK_ERROR_TIMEOUT))
                    }
                    is IOException -> {
                        emit(buildDialogError(NETWORK_ERROR))
                    }
                    is HttpException -> {
                        emit(buildDialogError(convertErrorBody(it)))
                    }
                    else -> {
                        emit(buildDialogError(UNKNOWN_ERROR))
                    }
                }
            }
        }
    }

    private suspend fun safeCacheCall() = flow<DataState<ViewState>> {
        withContext(dispatcher) {
            kotlin.runCatching {
                withTimeout(CACHE_TIMEOUT) {
                    val result = cacheCall?.invoke()
                    handleCacheSuccess(result)?.let {
                        emit(it)
                    }
                }
            }.onFailure {
                when (it) {
                    is TimeoutCancellationException -> {
                        emit(buildDialogError(CACHE_ERROR_TIMEOUT))
                    }
                    else -> {
                        emit(buildDialogError(UNKNOWN_ERROR))
                    }
                }
            }
        }
    }

    fun buildDialogError(
        message: String?
    ): DataState<ViewState> {
        return DataState.ERROR(
            StateMessage(
                message = message,
                uiComponentType = UIComponentType.DIALOG,
                messageType = MessageType.ERROR
            )
        )
    }

    private fun convertErrorBody(throwable: HttpException): String? =
        runCatching {
            throwable.response()?.errorBody()?.string()
        }.getOrDefault(UNKNOWN_ERROR)

    open suspend fun handleNetworkSuccess(response: NetworkObj):
        DataState<ViewState>? {
            return null
        }

    open suspend fun handleCacheSuccess(response: CacheObj?):
        DataState<ViewState>? {
            return null
        }

    open suspend fun updateCache(networkObject: NetworkObj) {
        // Waiting for override
    }
}
