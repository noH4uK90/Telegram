package com.example.telegram.presentation.states

import com.example.telegram.domain.models.UserInfoList

data class UsersState(
    val userInfoList: UserInfoList? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
