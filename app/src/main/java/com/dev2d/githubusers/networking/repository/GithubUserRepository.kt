package com.dev2d.githubusers.networking.repository

import com.dev2d.githubusers.data.UserDao
import com.dev2d.githubusers.networking.NetworkService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubUserRepository @Inject constructor(
    private val networkService: NetworkService,
    private val userDao: UserDao
) {
    suspend fun fetchUsers() {
        val users = networkService.fetchUsers()
        userDao.insertUser(*users.toTypedArray())
    }
}