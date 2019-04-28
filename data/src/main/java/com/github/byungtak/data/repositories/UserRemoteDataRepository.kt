package com.github.byungtak.data.repositories

import com.github.byungtak.data.api.ApiService
import com.github.byungtak.data.mappers.UserDataToEntityMapper
import com.github.byungtak.domain.UserRemoteRepository
import com.github.byungtak.domain.entities.UserEntity
import io.reactivex.Observable

class UserRemoteDataRepository(private val apiService: ApiService): UserRemoteRepository {

    private val userDataEntityMapper = UserDataToEntityMapper()

    override fun searchUser(query: String, currentPage: Int): Observable<List<UserEntity>> =
        apiService
            .searchUser(query, currentPage)
            .map { userModel ->
                userModel.items.map { userDataEntityMapper.mapFrom(it) }
            }

}