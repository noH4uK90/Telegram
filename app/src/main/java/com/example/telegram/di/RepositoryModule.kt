package com.example.telegram.di

import com.example.telegram.data.repository.RandomUserRepositoryImpl
import com.example.telegram.domain.repository.RandomUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRandomUserRepository(
        randomUserRepositoryImpl: RandomUserRepositoryImpl
    ): RandomUserRepository
}