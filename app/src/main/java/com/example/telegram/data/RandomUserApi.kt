package com.example.telegram.data

import com.example.telegram.data.network.NetworkUserInfoList
import retrofit2.http.GET

interface RandomUserApi {

    @GET("api/?results=20&inc=gender,name,email,login,phone,picture")
    suspend fun getUser(): NetworkUserInfoList
}