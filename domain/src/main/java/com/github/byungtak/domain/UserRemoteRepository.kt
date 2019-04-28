package com.github.byungtak.domain

import com.github.byungtak.domain.entities.UserEntity
import io.reactivex.Observable

interface UserRemoteRepository {

    fun searchUser(query: String, currentPage: Int): Observable<List<UserEntity>>

}