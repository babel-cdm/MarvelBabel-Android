package com.babel.marvel.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "responses")
data class CachedResponseAllCharacters(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "responseCharacters")
    var response: String
)
