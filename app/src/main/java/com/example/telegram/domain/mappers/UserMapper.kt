package com.example.telegram.domain.mappers

import com.example.telegram.domain.dataBase.entities.User
import com.example.telegram.domain.models.Login
import com.example.telegram.domain.models.Name
import com.example.telegram.domain.models.Picture
import com.example.telegram.domain.models.UserInfo

fun UserInfo.toUser(): User {
    return User(
        uuid = login.uuid,
        userName = login.username,
        firstName = name.first,
        lastName = name.last,
        email = email,
        phone = phone,
        gender = gender,
        picture = picture?.medium
    )
}

fun User.toUserInfo(): UserInfo {
    return UserInfo(
        login = Login(uuid = uuid, username = userName),
        name = Name(first = firstName, last = lastName),
        email = email,
        phone = phone,
        gender = gender,
        picture = Picture(medium = picture)
    )
}