package com.github.byungtak.githubsearch.data

import com.github.byungtak.githubsearch.data.model.User
import io.reactivex.Single

internal interface SearchRepository {
    fun searchUser(userText: String): Single<List<User>>
}