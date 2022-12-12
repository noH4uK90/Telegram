package com.example.telegram.domain.dataBase.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = false)
    val uuid: String,
    val userName: String,
    val firstName: String,
    val lastName: String?,
    val email: String?,
    val phone: String,
    val gender: String?,
    val picture: String?
)
