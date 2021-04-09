package com.babel.marvel.data.repository

import com.babel.marvel.data.datasource.cache.MarvelCacheDataSource
import com.babel.marvel.data.datasource.remote.MarvelRemoteDataSource
import com.babel.marvel.data.entity.DataCharacterEntity
import com.babel.marvel.data.mapper.toPresentation
import com.babel.marvel.domain.datastate.DataState
import com.babel.marvel.domain.datastate.MessageType
import com.babel.marvel.domain.datastate.StateMessage
import com.babel.marvel.domain.datastate.UIComponentType
import com.babel.marvel.domain.repository.MarvelRepository
import com.babel.marvel.domain.viewstate.*
import java.net.HttpURLConnection
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

@ExperimentalCoroutinesApi
class MarvelRepositoryImpl(
    private val marvelRemoteDataSource: MarvelRemoteDataSource,
    private val marvelCacheDataSource: MarvelCacheDataSource,
) : MarvelRepository {

    override fun getCharacters(): Flow<DataState<CharactersListViewState>> = flow {
        emitAll(
            object : NetworkBoundResource<DataCharacterEntity, DataCharacterEntity, CharactersListViewState>(
                IO,
                apiCall = { marvelRemoteDataSource.characters() },
                cacheCall = { marvelCacheDataSource.getAllCharacters() }
            ) {
                override suspend fun handleCacheSuccess(response: DataCharacterEntity?): DataState<CharactersListViewState>? {
                    return response?.let {
                        DataState.SUCCESS(
                            CharactersListViewState(
                                CharacterFields(
                                    response.data.results.toPresentation()
                                )
                            )
                        )
                    }
                }

                override suspend fun handleNetworkSuccess(response: DataCharacterEntity): DataState<CharactersListViewState>? {
                    return if (response.code == HttpURLConnection.HTTP_OK) {
                        marvelCacheDataSource.insertCharacters(resultsEntity = response)
                        DataState.SUCCESS(
                            CharactersListViewState(
                                CharacterFields(
                                    response.data.results.toPresentation()
                                )
                            )
                        )
                    } else {
                        emit(buildDialogError(response.status))
                        DataState.ERROR(
                            StateMessage(
                                message = response.status,
                                messageType = MessageType.ERROR,
                                uiComponentType = UIComponentType.DIALOG
                            )
                        )
                    }
                }

                override suspend fun updateCache(networkObject: DataCharacterEntity) {
                    marvelCacheDataSource.insertCharacters(resultsEntity = networkObject)
                }
            }.result
        )
    }

    override fun getSingleCharacter(idCharacter: Int): Flow<DataState<CharactersListViewState>> = flow {
        emitAll(
            object : NetworkBoundResource<DataCharacterEntity, DataCharacterEntity, CharactersListViewState>(
                IO,
                apiCall = { marvelRemoteDataSource.singleCharacter(idCharacter) }
            ) {
                override suspend fun handleNetworkSuccess(response: DataCharacterEntity): DataState<CharactersListViewState>? {
                    return if (response.code == HttpURLConnection.HTTP_OK) {
                        DataState.SUCCESS(
                            CharactersListViewState(
                                CharacterFields(
                                    response.data.results.toPresentation()
                                )
                            )
                        )
                    } else {
                        DataState.ERROR(
                            StateMessage(
                                message = response.status,
                                messageType = MessageType.ERROR,
                                uiComponentType = UIComponentType.DIALOG
                            )
                        )
                    }
                }
            }.result
        )
    }
}
