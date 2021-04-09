package com.babel.marvel.data.entity

data class ResultsEntity(
    val id: Int,
    val name: String,
    val description: String,
    val modified: String,
    val thumbnail: ThumbnailEntity,
    val resourceURI: String,
    val comics: GenericEventEntity,
    val series: GenericEventEntity,
    val stories: GenericEventEntity,
    val events: GenericEventEntity,
    val urls: List<UrlsEntity>
)

data class ThumbnailEntity(
    var path: String,
    var extension: String
)

data class GenericEventEntity(
    val available: Int,
    val collectionURI: String,
    val items: List<ItemsEntity>,
    val returned: Int
)

data class UrlsEntity(
    val type: String,
    val url: String
)

data class ItemsEntity(
    val resourceURI: String,
    val name: String
)
