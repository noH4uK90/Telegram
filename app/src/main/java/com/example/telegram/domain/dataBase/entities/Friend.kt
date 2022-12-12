package com.example.telegram.domain.dataBase.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "friend",
    foreignKeys = [ForeignKey(entity = User::class,
        parentColumns = ["uuid"],
        childColumns = ["userId"])])
data class Friend(
    @PrimaryKey
    val userId: String,
    val friendId: String
)
