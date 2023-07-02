package com.prasad.abbreviationsfinder.model

import androidx.room.PrimaryKey

data class Bookmarks(
    var word: String,
    var meanings: List<String> = listOf(),
    var isExpanded:Boolean = false
)