package com.prasad.abbreviationsfinder.model

data class AcronymDataItem(
   val longForms: List<LongForms> = listOf(),
   val sf: String
)