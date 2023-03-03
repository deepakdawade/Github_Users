package com.dev2d.githubusers.networking.repository

import com.dev2d.githubusers.data.User
import com.dev2d.githubusers.data.UserDao
import com.dev2d.githubusers.networking.NetworkService
import com.dev2d.githubusers.networking.response.Subscription
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
    fun getUser(id: String): Flow<User> = userDao.getUser(id)

    suspend fun getFollowers(loginId: String): List<User> {
        return networkService.getFollowers(loginId = loginId)
    }

    suspend fun getSubscriptions(loginId: String): List<Subscription> {
        return networkService.getSubscriptions(loginId = loginId)
    }
}