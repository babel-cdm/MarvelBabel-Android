package com.babel.marvel.data.koin

import com.babel.marvel.data.repository.MarvelRepositoryImpl
import com.babel.marvel.domain.repository.MarvelRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val dataModule = module {
    factory<MarvelRepository> {
        MarvelRepositoryImpl(
            get(),
            get()
        )
    }
}
