package com.github.byungtak.githubsearch.data

import com.github.byungtak.githubsearch.data.model.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

internal interface UserRepository {

    fun updateFavoriteUser(user: User): Completable
    fun searchUser(userText: String): Single<List<User>>
    fun getFavoriteUsers(): Flowable<List<User>>

}