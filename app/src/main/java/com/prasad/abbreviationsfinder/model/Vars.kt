package com.prasad.abbreviationsfinder.model

import com.google.gson.annotations.SerializedName

data class Vars(
    @SerializedName("frequent") val freq: Int,
    @SerializedName("longForm") val lf: String,
    @SerializedName("since") val since: Int
)