package com.babel.marvel.data.mapper

import com.babel.marvel.data.entity.*
import com.babel.marvel.domain.viewstate.*

fun List<ResultsEntity>.toPresentation(): List<Character> = this.map {
    Character(
        id = it.id,
        name = it.name,
        description = it.description,
        modified = it.modified,
        thumbnail = it.thumbnail.toPresentation(),
        resourceURI = it.resourceURI,
        comics = it.comics.toPresentation(),
        series = it.series.toPresentation(),
        stories = it.stories.toPresentation(),
        events = it.events.toPresentation(),
        urls = it.urls.toPresentationMapper()
    )
}

fun ThumbnailEntity.toPresentation() = Thumbnail(
    path = this.path,
    extension = this.extension
)

fun GenericEventEntity.toPresentation() = GenericEvent(
    available = this.available,
    collectionURI = this.collectionURI,
    items = this.items.map {
        Items(
            resourceURI = it.resourceURI,
            name = it.name
        )
    },
    returned = this.returned,
)

fun List<UrlsEntity>.toPresentationMapper(): List<Urls> = this.map {
    Urls(
        type = it.type,
        url = it.url
    )
}
