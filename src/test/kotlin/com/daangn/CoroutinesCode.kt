package com.daangn

import com.daangn.AsynchronousProgrammingInJava.GithubInfo
import com.daangn.GithubOperations.Organization
import com.daangn.GithubOperations.Repository
import com.daangn.GithubOperations.User
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking

class CoroutinesGithubOperations(accessToken: String) {

    private val operations = GithubOperations(accessToken)

    suspend fun fetchUser(username: String): User {
        return operations.fetchUserAsync(username).await()
    }

    suspend fun fetchOrganizations(user: User): List<Organization> {
        return operations.fetchOrganizationsAsync(user).await()
    }

    suspend fun fetchRepositories(user: User): List<Repository> {
        return operations.fetchRepositoriesAsync(user).await()
    }

}

suspend fun getGithubInfo(username: String, accessToken: String) = coroutineScope {
    val operations = CoroutinesGithubOperations(accessToken)

    val user: User = operations.fetchUser(username)
    val organizations = async { operations.fetchOrganizations(user) }
    val repositories = async { operations.fetchRepositories(user) }

    GithubInfo(user, organizations.await(), repositories.await())
}

fun main(args: Array<String>) {
    val username = "arawn"
    val accessToken = "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"

    val githubInfo = runBlocking {
        getGithubInfo(username, accessToken)
    }
    println(githubInfo.user)
    println(githubInfo.organizations)
    println(githubInfo.repositories)
}
