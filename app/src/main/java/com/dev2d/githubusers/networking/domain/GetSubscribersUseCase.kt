package com.dev2d.githubusers.networking.domain

import com.dev2d.githubusers.data.User
import com.dev2d.githubusers.networking.repository.GithubUserRepository
import com.dev2d.githubusers.networking.result.InvokeResultUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSubscribersUseCase @Inject constructor(
    private val repository: GithubUserRepository
) : InvokeResultUseCase<String, List<User>>() {

    override suspend fun doWork(params: String): List<User> {
        return withContext(Dispatchers.IO) {
            repository.getSubscribers(params)
        }
    }
}