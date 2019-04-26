package com.github.byungtak.githubsearch.data.local

import com.github.byungtak.githubsearch.data.model.User
import io.reactivex.Completable
import io.reactivex.Flowable

internal interface UserLocalRepository {

    fun addFavoriteUser(user: User): Completable
    fun removeFavoriteUser(userid: String): Completable
    fun getFavoriteUsers(): Flowable<List<User>>

}