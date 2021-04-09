package com.babel.marvel.cache.koin

import androidx.room.Room
import com.babel.marvel.cache.db.AppDatabase
import com.babel.marvel.cache.source.MarvelCacheDataSourceImpl
import com.babel.marvel.data.datasource.cache.MarvelCacheDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }
    single { get<AppDatabase>().getMarvelDao() }

    factory<MarvelCacheDataSource> {
        MarvelCacheDataSourceImpl(
            get()
        )
    }
}
