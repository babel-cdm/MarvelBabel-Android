package com.babel.marvel.cache.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.babel.marvel.cache.model.CachedResponseAllCharacters

@Dao
interface MarvelDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnore(cachedResponseAllCharacters: CachedResponseAllCharacters)

    @Query("SELECT * FROM responses")
    suspend fun getCachedData(): CachedResponseAllCharacters?
}
