package com.prasad.abbreviationsfinder.model

import com.google.gson.annotations.SerializedName

data class AcronymDataItem(
    @SerializedName("longForms") val longForms: List<LongForms> = listOf(),
    @SerializedName("shortForms") val sf: String
)