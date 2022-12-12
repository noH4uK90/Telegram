package com.example.telegram.data.network

import com.squareup.moshi.Json

data class NetworkName(
    @field:Json(name = "first")
    val first: String,
    @field:Json(name = "last")
    val last: String
)