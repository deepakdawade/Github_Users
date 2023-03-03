package com.dev2d.githubusers.networking.domain

import com.dev2d.githubusers.networking.repository.GithubUserRepository
import com.dev2d.githubusers.networking.response.Subscription
import com.dev2d.githubusers.networking.result.InvokeResultUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSubscriptionsUseCase @Inject constructor(
    private val repository: GithubUserRepository
) : InvokeResultUseCase<String, List<Subscription>>() {

    override suspend fun doWork(params: String): List<Subscription> {
        return withContext(Dispatchers.IO) {
            repository.getSubscriptions(params)
        }
    }
}