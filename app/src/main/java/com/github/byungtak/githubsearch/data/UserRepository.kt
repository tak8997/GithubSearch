package com.github.byungtak.githubsearch.data

import com.github.byungtak.githubsearch.data.model.User
import io.reactivex.Completable
import io.reactivex.Single

internal interface UserRepository {

    fun addFavoriteUser(user: User): Completable
    fun searchUser(userText: String): Single<List<User>>

}