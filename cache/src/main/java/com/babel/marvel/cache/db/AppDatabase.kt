package com.babel.marvel.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.babel.marvel.cache.model.CachedResponseAllCharacters

@Database(entities = [CachedResponseAllCharacters::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getMarvelDao(): MarvelDao

    companion object {
        const val DATABASE_NAME = "app_marvel_db"
    }
}
