package com.dev2d.githubusers.networking

import com.dev2d.githubusers.data.User
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkService {
    @GET(EndPoints.USERS)
    suspend fun fetchUsers(): List<User>

    @GET(EndPoints.FOLLOWERS)
    suspend fun getFollowers(
        @Path("loginId") loginId: String
    ): List<User>

    @GET(EndPoints.SUBSCRIBERS)
    suspend fun getSubscribers(
        @Path("loginId") loginId: String
    ): List<User>

}