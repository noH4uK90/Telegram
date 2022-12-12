package com.example.telegram.presentation.states

import com.example.telegram.domain.models.UserInfo

data class FriendState(
    val usersInfo: List<UserInfo>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
