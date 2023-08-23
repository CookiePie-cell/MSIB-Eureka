package com.salugan.githubuser.data

import com.salugan.githubuser.data.remote.model.responses.UserResponse
import com.salugan.githubuser.data.remote.model.responses.DetailUserResponse
import com.salugan.githubuser.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository private constructor(
    private val apiService: ApiService,
    ) {

    fun getListUsers(): Flow<Result<List<UserResponse>>> = flow {
        emit(Result.Loading)
        try {
            val response: List<UserResponse> = apiService.getListUsers()
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getDetailUser(username: String): Flow<Result<DetailUserResponse>> = flow {
        emit(Result.Loading)
        try {
            val response: DetailUserResponse = apiService.getDetailUser(username)
            val user: DetailUserResponse = response
            emit(Result.Success(user))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getFollowers(username: String): Flow<Result<List<UserResponse>>> = flow {
        emit(Result.Loading)
        try {
            val followers: List<UserResponse> = apiService.getFollowers(username)
            emit(Result.Success(followers))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getFollowing(username: String): Flow<Result<List<UserResponse>>> = flow {
        emit(Result.Loading)
        try {
            val followers: List<UserResponse> = apiService.getFollowing(username)
            emit(Result.Success(followers))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)


    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService)
            }.also { instance = it }
    }
}