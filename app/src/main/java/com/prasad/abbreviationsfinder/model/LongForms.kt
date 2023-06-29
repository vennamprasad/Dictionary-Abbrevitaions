package com.prasad.abbreviationsfinder.model

data class LongForms(
    val freq: Int,
    val lf: String,
    val since: Int,
    val vars: List<Vars> = listOf(),
)