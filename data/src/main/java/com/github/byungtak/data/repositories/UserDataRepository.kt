package com.github.byungtak.data.repositories

import com.github.byungtak.domain.UserLocalRepository
import com.github.byungtak.domain.UserRemoteRepository
import com.github.byungtak.domain.UserRepository
import com.github.byungtak.domain.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable

class UserDataRepository(
    private val userLocalRepository: UserLocalRepository,
    private val searchRemoteRepository: UserRemoteRepository
): UserRepository {

    override fun searchUser(query: String, currentPage: Int): Observable<List<UserEntity>>
            = searchRemoteRepository.searchUser(query, currentPage)

    override fun saveFavoriteUser(userEntity: UserEntity): Completable
            = userLocalRepository.saveFavoriteUser(userEntity)

    override fun removeFavoriteUser(userEntity: UserEntity): Completable
            = userLocalRepository.removeFavoriteUser(userEntity.id.toString())

    override fun getFavoriteUsers(): Observable<List<UserEntity>>
            = userLocalRepository.getFavoriteUsers()

}