package com.babel.marvel.remote.service

import com.babel.marvel.data.entity.DataCharacterEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApiService {
    @GET("characters")
    suspend fun getCharacters(): DataCharacterEntity

    @GET("characters/{ID_CHARACTER}")
    suspend fun getSingleCharacter(
        @Path("ID_CHARACTER") idCharacter: String
    ): DataCharacterEntity
}
