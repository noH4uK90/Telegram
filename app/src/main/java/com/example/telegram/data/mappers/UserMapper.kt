package com.example.telegram.data.mappers

import com.example.telegram.data.network.*
import com.example.telegram.domain.models.*

fun NetworkUserInfoList.toUserInfoList(): UserInfoList {
    return UserInfoList(
        items = items.map { it.toUserInfo() }
    )
}

fun NetworkUserInfo.toUserInfo(): UserInfo {
    return UserInfo(
        login = login.toLogin(),
        name = name.toName(),
        gender = gender,
        email = email,
        phone = phone,
        picture = picture.toPicture()
    )
}

fun NetworkLogin.toLogin(): Login {
    return Login(
        uuid = uuid,
        username = username,
        /*password = password,
        salt = salt,
        md5 = md5,
        sha1 = sha1,
        sha256 = sha256*/
    )
}

fun NetworkName.toName(): Name {
    return Name(
        first = first,
        last = last
    )
}

fun NetworkPicture.toPicture(): Picture {
    return Picture(
        /*thumbnail = thumbnail,
        large = large,*/
        medium = medium
    )
}