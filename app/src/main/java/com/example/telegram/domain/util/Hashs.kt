package com.example.telegram.data

import java.security.MessageDigest

fun String.toMd5(): String {
    return hashString(this, "MD5")
}

fun String.toSha1(): String {
    return hashString(this, "SHA-1")
}

fun String.toSha256(): String {
    return hashString(this, "SHA-256")
}

private fun hashString(input: String, algorithm: String): String {
    return MessageDigest
        .getInstance(algorithm)
        .digest(input.toByteArray())
        .fold("", { str, it -> str + "%02x".format(it) })
}