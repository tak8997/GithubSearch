package com.github.byungtak.domain

import com.github.byungtak.domain.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable

interface UserRepository {

    fun saveFavoriteUser(userEntity: UserEntity): Completable
    fun removeFavoriteUser(userEntity: UserEntity): Completable
    fun searchUser(query: String, currentPage: Int): Observable<List<UserEntity>>
    fun getFavoriteUsers(): Observable<List<UserEntity>>

}