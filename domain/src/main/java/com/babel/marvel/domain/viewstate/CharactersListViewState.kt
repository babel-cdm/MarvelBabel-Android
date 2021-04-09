package com.babel.marvel.domain.viewstate

import com.babel.marvel.domain.Constants.Companion.NETWORK_ERROR
import com.babel.marvel.domain.Constants.Companion.SUCCESS
import java.io.Serializable

data class CharactersListViewState(
    var characterFields: CharacterFields? = null,
    var idSelected: Int? = null
) : Serializable

data class CharacterFields(
    var listCharacters: List<Character>? = null
) {
    class GettingCharactersError {
        companion object {
            fun genericError(): String {
                return NETWORK_ERROR
            }

            fun none(): String {
                return SUCCESS
            }
        }
    }
}

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val modified: String,
    val thumbnail: Thumbnail,
    val resourceURI: String,
    val comics: GenericEvent,
    val series: GenericEvent,
    val stories: GenericEvent,
    val events: GenericEvent,
    val urls: List<Urls>
)

data class Thumbnail(
    var path: String? = null,
    var extension: String? = null
)

data class GenericEvent(
    val available: Int,
    val collectionURI: String,
    val items: List<Items>,
    val returned: Int
)

data class Urls(
    val type: String,
    val url: String
)

data class Items(
    val resourceURI: String,
    val name: String
)
