package com.example.telegram.data.repository

import com.example.telegram.data.RandomUserApi
import com.example.telegram.data.mappers.toUserInfoList
import com.example.telegram.domain.models.UserInfoList
import com.example.telegram.domain.repository.RandomUserRepository
import com.example.telegram.domain.util.Resource
import javax.inject.Inject

class RandomUserRepositoryImpl @Inject constructor(
    private val api: RandomUserApi
): RandomUserRepository {

    override suspend fun getUser(): Resource<UserInfoList> {
        return try {
            Resource.Success(
                data = api.getUser().toUserInfoList()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "Неизвестная ошибка")
        }
    }
}