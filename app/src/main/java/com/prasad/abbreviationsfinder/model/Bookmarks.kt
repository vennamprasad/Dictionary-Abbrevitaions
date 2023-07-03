package com.prasad.abbreviationsfinder.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Bookmarks(
    var word: String,
    var meanings: List<String> = listOf(),
) : Parcelable