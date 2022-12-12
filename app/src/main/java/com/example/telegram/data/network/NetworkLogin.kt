package com.example.telegram.data.network

import com.squareup.moshi.Json

data class NetworkLogin(
    @field:Json(name = "uuid")
    val uuid: String,
    @field:Json(name = "username")
    val username: String,
    /*val password: String,
    val salt: String,
    val md5: String,
    val sha1: String,
    val sha256: String*/
)