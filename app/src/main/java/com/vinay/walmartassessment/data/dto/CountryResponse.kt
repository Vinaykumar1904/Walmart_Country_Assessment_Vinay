package com.vinay.walmartassessment.data.dto
/*
* Data class to hold Api Response
* */
data class CountryResponse(
    val capital: String,
    val code: String,
    val currency: Currency,
    val demonym: String,
    val flag: String,
    val language: Language,
    val name: String,
    val region: String
)