package com.babel.marvel.domain.usecase

import com.babel.marvel.domain.datastate.DataState
import com.babel.marvel.domain.repository.MarvelRepository
import com.babel.marvel.domain.viewstate.CharactersListViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetSingleCharacterUseCase(private val repository: MarvelRepository) {
    fun invoke(idCharacter: Int): Flow<DataState<CharactersListViewState>> {
        return repository.getSingleCharacter(idCharacter)
            .flowOn(Dispatchers.IO)
    }
}
