package com.babel.marvel.koin

import com.babel.marvel.cache.koin.databaseModule
import com.babel.marvel.data.koin.dataModule
import com.babel.marvel.domain.koin.useCaseModule
import com.babel.marvel.remote.koin.remoteModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
val koinModules = listOf(
    databaseModule,
    remoteModule,
    dataModule,
    useCaseModule,
    authModule,
    mainModule
)
