package com.prasad.abbreviationsfinder.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DICTIONARY")
data class BookmarkEntity(

    @PrimaryKey var word: String,
    var meanings: List<String> = listOf(),
)
