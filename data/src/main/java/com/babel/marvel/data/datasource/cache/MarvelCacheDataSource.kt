package com.babel.marvel.data.datasource.cache

import com.babel.marvel.data.entity.DataCharacterEntity

interface MarvelCacheDataSource {

    suspend fun insertCharacters(resultsEntity: DataCharacterEntity)

    suspend fun searchById(idCharacter: Int): DataCharacterEntity?

    suspend fun getAllCharacters(): DataCharacterEntity?
}
