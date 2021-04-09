package com.babel.marvel.data.entity

data class DataCharacterEntity(
    var data: DataEntity
) : BaseEntity()

data class DataEntity(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: MutableList<ResultsEntity>
)
