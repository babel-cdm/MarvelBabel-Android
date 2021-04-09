package com.babel.marvel.features.main.detail

import com.babel.marvel.base.BaseViewModel
import com.babel.marvel.domain.datastate.DataState
import com.babel.marvel.domain.usecase.GetSingleCharacterUseCase
import com.babel.marvel.domain.viewstate.CharactersListViewState
import com.babel.marvel.events.MarvelEventState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

@FlowPreview
@ExperimentalCoroutinesApi
class DetailViewModel(
    private val getSingleCharacterUseCase: GetSingleCharacterUseCase
) : BaseViewModel<MarvelEventState, CharactersListViewState>() {

    override fun handleEventState(eventState: MarvelEventState): Flow<DataState<CharactersListViewState>> =
        flow {
            when (eventState) {
                is MarvelEventState.ShowCharacterEvent -> {
                    getCurrentViewStateOrNew().idSelected?.let {
                        emitAll(getSingleCharacterUseCase.invoke(it))
                    }
                }
            }
        }

    override fun initNewViewState(): CharactersListViewState = CharactersListViewState()
}
