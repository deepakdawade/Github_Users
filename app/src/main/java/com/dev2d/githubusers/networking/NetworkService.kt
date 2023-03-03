package com.dev2d.githubusers.networking

import com.dev2d.githubusers.data.User
import retrofit2.http.GET

interface NetworkService {
    @GET(EndPoints.users)
    suspend fun fetchUsers(): List<User>
}