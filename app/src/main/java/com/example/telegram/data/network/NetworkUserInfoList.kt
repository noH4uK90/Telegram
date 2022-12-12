package com.example.telegram.data.network

import com.squareup.moshi.Json

data class NetworkUserInfoList(
    @field:Json(name = "results")
    val items: List<NetworkUserInfo>
)
