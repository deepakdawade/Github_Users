package com.dev2d.githubusers.networking.repository

import com.dev2d.githubusers.data.User
import com.dev2d.githubusers.data.UserDao
import com.dev2d.githubusers.networking.NetworkService
import kotlinx.coroutines.flow.Flow
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

    fun getUsers(): Flow<List<User>> = userDao.allUsers()
}