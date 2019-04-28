package com.github.byungtak.domain

import com.github.byungtak.domain.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface UserLocalRepository {

    fun saveFavoriteUser(user: UserEntity): Completable
    fun removeFavoriteUser(userid: String): Completable
    fun getFavoriteUsers(): Observable<List<UserEntity>>

}