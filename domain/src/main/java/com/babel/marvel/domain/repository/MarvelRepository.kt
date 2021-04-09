package com.babel.marvel.domain.repository

import com.babel.marvel.domain.datastate.DataState
import com.babel.marvel.domain.viewstate.CharactersListViewState
import kotlinx.coroutines.flow.Flow

interface MarvelRepository {

    fun getCharacters(): Flow<DataState<CharactersListViewState>>

    fun getSingleCharacter(
        idCharacter: Int
    ): Flow<DataState<CharactersListViewState>>
}
