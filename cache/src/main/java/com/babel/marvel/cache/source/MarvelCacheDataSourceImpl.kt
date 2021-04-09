package com.babel.marvel.cache.source

import com.babel.marvel.cache.db.MarvelDao
import com.babel.marvel.cache.model.CachedResponseAllCharacters
import com.babel.marvel.data.datasource.cache.MarvelCacheDataSource
import com.babel.marvel.data.entity.DataCharacterEntity
import com.google.gson.Gson

class MarvelCacheDataSourceImpl(
    private val marvelDao: MarvelDao
) : MarvelCacheDataSource {

    override suspend fun insertCharacters(resultsEntity: DataCharacterEntity) {
        marvelDao.insertOrIgnore(
            CachedResponseAllCharacters(
                response = Gson().toJson(resultsEntity)
            )
        )
    }

    override suspend fun searchById(idCharacter: Int): DataCharacterEntity? {
        val listCached = marvelDao.getCachedData()?.let {
            Gson().fromJson(it.response, DataCharacterEntity::class.java)
        }

        listCached?.let {
            val data = listCached.data.results.firstOrNull { it.id == idCharacter }
            it.data.results.clear()
            it.data.results.add(data!!)
        }

        return listCached
    }

    override suspend fun getAllCharacters(): DataCharacterEntity? {
        return marvelDao.getCachedData()?.let {
            Gson().fromJson(it.response, DataCharacterEntity::class.java)
        }
    }
}
