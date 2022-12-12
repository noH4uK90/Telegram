package com.example.telegram.domain.models

data class PhoneCode(
    val region: String,
    val countryName: String,
    val code: Int
)
