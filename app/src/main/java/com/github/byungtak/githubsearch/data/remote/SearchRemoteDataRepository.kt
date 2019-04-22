package com.github.byungtak.githubsearch.data.remote

import com.github.byungtak.githubsearch.data.model.User
import io.reactivex.Single

internal class SearchRemoteDataRepository(private val apiService: ApiService): SearchRemoteRepository {

    override fun searchUser(userText: String): Single<List<User>> = apiService
        .searchUser(userText)
        .map { it.items }

}