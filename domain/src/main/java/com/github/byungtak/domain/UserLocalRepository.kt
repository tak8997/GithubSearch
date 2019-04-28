package com.github.byungtak.domain

import com.github.byungtak.domain.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface UserLocalRepository {

    fun saveFavoriteUser(user: UserEntity): Completable
    fun removeFavoriteUser(userEntity: UserEntity): Completable
    fun removeAllUsers(): Completable
    fun getFavoriteUsers(): Observable<List<UserEntity>>

}