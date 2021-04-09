package com.babel.marvel.base

import androidx.lifecycle.*
import com.babel.marvel.domain.datastate.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
abstract class BaseViewModel<EventState, ViewState> : ViewModel() {
    private val dataChannel: ConflatedBroadcastChannel<DataState<ViewState>> =
        ConflatedBroadcastChannel()

    private val _viewState: MutableLiveData<ViewState> = MutableLiveData()

    private val viewState: LiveData<ViewState>
        get() = _viewState

    private val _dataState: MutableLiveData<DataState<ViewState>> = MutableLiveData()

    val dataState: LiveData<DataState<ViewState>> = _dataState

    init {
        dataChannel
            .asFlow()
            .onEach { dataState ->
                _dataState.value = dataState
            }
            .launchIn(viewModelScope)
    }

    fun setEventState(eventState: EventState) {
        dataChannel.let { channel ->
            handleEventState(eventState).onEach { data ->
                if (!channel.isClosedForSend) {
                    channel.offer(data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun setViewState(viewState: ViewState) {
        _viewState.value = viewState
    }

    fun getCurrentViewStateOrNew(): ViewState {
        return viewState.value ?: initNewViewState()
    }

    abstract fun handleEventState(eventState: EventState): Flow<DataState<ViewState>>

    abstract fun initNewViewState(): ViewState
}
