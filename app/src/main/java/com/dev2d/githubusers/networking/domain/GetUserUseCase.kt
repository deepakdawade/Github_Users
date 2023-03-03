package com.dev2d.githubusers.networking.domain

import com.dev2d.githubusers.data.User
import com.dev2d.githubusers.networking.repository.GithubUserRepository
import com.dev2d.githubusers.networking.result.SubjectUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: GithubUserRepository
) : SubjectUseCase<Unit, List<User>>() {
    override fun createObservable(params: Unit): Flow<List<User>> {
        return repository.getUsers()
    }
}