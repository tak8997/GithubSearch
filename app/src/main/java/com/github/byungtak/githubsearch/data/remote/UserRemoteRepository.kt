package com.github.byungtak.githubsearch.data.remote

import com.github.byungtak.githubsearch.data.model.User
import io.reactivex.Single

internal interface UserRemoteRepository {

    fun searchUser(userText: String): Single<List<User>>

}