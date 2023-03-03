package com.dev2d.githubusers.networking.domain

import com.dev2d.githubusers.networking.repository.GithubUserRepository
import com.dev2d.githubusers.networking.result.InvokeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FetchUsersUseCase @Inject constructor(
    private val repository: GithubUserRepository
) : InvokeUseCase<Unit>() {
    override suspend fun doWork(params: Unit) {
        withContext(Dispatchers.IO) {
            repository.fetchUsers()
        }
    }
}