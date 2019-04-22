package com.github.byungtak.githubsearch.data.remote

import com.github.byungtak.githubsearch.data.model.User
import io.reactivex.Single

internal interface SearchRemoteRepository {

    fun searchUser(userText: String): Single<List<User>>

}