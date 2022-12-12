package com.example.telegram.domain.models

data class UserInfo(
    val login: Login,
    val name: Name,
    val email: String?,
    val gender: String?,
    val phone: String,
    val picture: Picture?
)