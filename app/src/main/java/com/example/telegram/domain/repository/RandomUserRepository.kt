package com.example.telegram.domain.repository

import com.example.telegram.domain.models.UserInfoList
import com.example.telegram.domain.util.Resource

interface RandomUserRepository {
    suspend fun getUser(): Resource<UserInfoList>
}