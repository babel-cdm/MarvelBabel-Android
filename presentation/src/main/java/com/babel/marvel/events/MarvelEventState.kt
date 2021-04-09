package com.babel.marvel.events

sealed class MarvelEventState {

    object LoadingDataEvent : MarvelEventState()

    data class ShowCharacterEvent(
        val idCharacter: Int
    ) : MarvelEventState()

    object None : MarvelEventState()
}
