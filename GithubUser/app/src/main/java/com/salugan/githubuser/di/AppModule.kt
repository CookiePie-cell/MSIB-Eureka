package com.salugan.githubuser.di

import com.salugan.githubuser.data.UserRepository
import com.salugan.githubuser.data.remote.retrofit.ApiConfig
import com.salugan.githubuser.data.remote.retrofit.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService = ApiConfig.getApiService()

    @Provides
    @Singleton
    fun provideUserRepository(api: ApiService): UserRepository = UserRepository.getInstance(api)
}