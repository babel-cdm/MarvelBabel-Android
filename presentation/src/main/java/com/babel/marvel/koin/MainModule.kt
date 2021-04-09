package com.babel.marvel.koin

import com.babel.marvel.features.main.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@FlowPreview
@ExperimentalCoroutinesApi
val authModule = module {
    viewModel { MainViewModel(get()) }
}
