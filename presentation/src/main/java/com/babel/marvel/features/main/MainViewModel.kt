package com.babel.marvel.features.main

import com.babel.marvel.base.BaseViewModel
import com.babel.marvel.domain.datastate.DataState
import com.babel.marvel.domain.usecase.GetAllCharacterUseCase
import com.babel.marvel.domain.viewstate.CharactersListViewState
import com.babel.marvel.events.MarvelEventState
import com.babel.marvel.events.MarvelEventState.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

@FlowPreview
@ExperimentalCoroutinesApi
class MainViewModel(
    private val getAllCharacterUseCase: GetAllCharacterUseCase
) : BaseViewModel<MarvelEventState, CharactersListViewState>() {

    override fun initNewViewState(): CharactersListViewState = CharactersListViewState()

    override fun handleEventState(eventState: MarvelEventState): Flow<DataState<CharactersListViewState>> =
        flow {
            when (eventState) {
                is LoadingDataEvent -> {
                    emitAll(getAllCharacterUseCase.invoke())
                }
            }
        }

    fun registerIdSelected(idSelected: Int) {
        setViewState(
            getCurrentViewStateOrNew().copy(
                idSelected = idSelected
            )
        )
    }
}
