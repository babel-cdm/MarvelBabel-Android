package com.babel.marvel.remote.source

import com.babel.marvel.data.datasource.remote.MarvelRemoteDataSource
import com.babel.marvel.data.entity.DataCharacterEntity
import com.babel.marvel.remote.service.MarvelApiService

class MarvelRemoteDataSourceImpl(
    private val marvelApiService: MarvelApiService
) : MarvelRemoteDataSource {

    override suspend fun characters(): DataCharacterEntity {
        return marvelApiService.getCharacters()
    }

    override suspend fun singleCharacter(idCharacter: Int): DataCharacterEntity {
        return marvelApiService.getSingleCharacter(idCharacter.toString())
    }
}
