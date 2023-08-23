package com.salugan.githubuser.di

import com.salugan.githubuser.data.UserRepository
import com.salugan.githubuser.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }

}