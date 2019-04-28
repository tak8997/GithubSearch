package com.github.byungtak.githubsearch.data.remote

import com.github.byungtak.githubsearch.data.model.User
import io.reactivex.Single

internal class UserRemoteDataRepository(private val apiService: ApiService): UserRemoteRepository {

    override fun searchUser(query: String, currentPage: Int): Single<List<User>> = apiService
        .searchUser(query, currentPage)
        .map { it.items }

}