package com.prasad.abbreviationsfinder.model

import com.google.gson.annotations.SerializedName

data class LongForms(
    @SerializedName("freq") val freq: Int,
    @SerializedName("longForm") val lf: String,
    @SerializedName("since") val since: Int,
    @SerializedName("vars") val vars: List<Vars> = listOf(),
)