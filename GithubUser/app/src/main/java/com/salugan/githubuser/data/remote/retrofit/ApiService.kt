package com.salugan.githubuser.data.remote.retrofit

import com.salugan.githubuser.data.remote.model.responses.UserResponse
import com.salugan.githubuser.data.remote.model.responses.DetailUserResponse
import retrofit2.http.*

interface ApiService {
    @GET("/users")
    suspend fun getListUsers() : List<UserResponse>

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ) : DetailUserResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String
    ): List<UserResponse>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String
    ): List<UserResponse>
}