package com.babel.marvel.domain.koin

import com.babel.marvel.domain.usecase.GetAllCharacterUseCase
import com.babel.marvel.domain.usecase.GetSingleCharacterUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val useCaseModule = module {
    factory { GetAllCharacterUseCase(get()) }
    factory { GetSingleCharacterUseCase(get()) }
}
