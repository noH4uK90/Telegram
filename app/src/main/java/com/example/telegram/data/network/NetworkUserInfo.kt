package com.example.telegram.data.network

import com.squareup.moshi.Json

data class NetworkUserInfo(
    @field:Json(name = "login")
    val login: NetworkLogin,
    @field:Json(name = "name")
    val name: NetworkName,
    @field:Json(name = "email")
    val email: String,
    @field:Json(name = "gender")
    val gender: String,
    @field:Json(name = "phone")
    val phone: String,
    @field:Json(name = "picture")
    val picture: NetworkPicture
)