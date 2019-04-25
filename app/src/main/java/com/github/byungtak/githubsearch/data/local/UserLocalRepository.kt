package com.github.byungtak.githubsearch.data.local

import com.github.byungtak.githubsearch.data.model.User
import io.reactivex.Completable

internal interface UserLocalRepository {

    fun addFavoriteUser(user: User): Completable
    fun deleteFavoriteUser(userid: String): Completable

}