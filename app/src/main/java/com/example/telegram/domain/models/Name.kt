package com.example.telegram.domain.models

import com.squareup.moshi.Json

data class Name(
    val first: String,
    val last: String?
)