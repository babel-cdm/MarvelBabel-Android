package com.babel.marvel

import com.babel.marvel.domain.datastate.DataState
import com.babel.marvel.domain.repository.MarvelRepository
import com.babel.marvel.domain.usecase.GetAllCharacterUseCase
import com.babel.marvel.domain.usecase.GetSingleCharacterUseCase
import com.babel.marvel.domain.viewstate.CharactersListViewState
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.junit.Assert

class RepositoryUnitTest {

    private var repository: MarvelRepository = mock()
    private lateinit var getAllCharacterUseCase: GetAllCharacterUseCase
    private lateinit var getSingleCharacterUseCase: GetSingleCharacterUseCase

    @Before
    fun setUp(){
        getAllCharacterUseCase = GetAllCharacterUseCase(repository)
        getSingleCharacterUseCase = GetSingleCharacterUseCase(repository)
    }

    @Test
    fun `Get character list`() = runBlocking {
        val repoResult : Flow<DataState<CharactersListViewState>> = getAllCharacterUseCase.invoke()
        Mockito.`when`(repository.getCharacters()).thenReturn(repoResult)
        val result: Flow<DataState<CharactersListViewState>> = getAllCharacterUseCase.invoke()
        Assert.assertEquals(repoResult, result)
    }

    @Test
    fun `Get single character`() = runBlocking {
        val repoResult : Flow<DataState<CharactersListViewState>> = getSingleCharacterUseCase.invoke(DEFAULT_ID_CHARACTER)
        Mockito.`when`(repository.getSingleCharacter(DEFAULT_ID_CHARACTER)).thenReturn(repoResult)
        val result: Flow<DataState<CharactersListViewState>> = getSingleCharacterUseCase.invoke(DEFAULT_ID_CHARACTER)
        Assert.assertEquals(repoResult, result)
    }

    companion object {
        const val DEFAULT_ID_CHARACTER = 123
    }

}