package com.babel.marvel.data.datasource.remote

import com.babel.marvel.data.entity.DataCharacterEntity

interface MarvelRemoteDataSource {

    suspend fun characters():
        DataCharacterEntity

    suspend fun singleCharacter(
        idCharacter: Int
    ): DataCharacterEntity
}
