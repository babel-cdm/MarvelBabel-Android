package com.babel.marvel.domain.usecase

import com.babel.marvel.domain.datastate.DataState
import com.babel.marvel.domain.repository.MarvelRepository
import com.babel.marvel.domain.viewstate.CharactersListViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetAllCharacterUseCase(private val repository: MarvelRepository) {
    fun invoke(): Flow<DataState<CharactersListViewState>> {
        return repository.getCharacters()
            .flowOn(Dispatchers.IO)
    }
}
