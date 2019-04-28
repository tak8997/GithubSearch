package com.github.byungtak.domain.usecases

import com.github.byungtak.domain.UserRepository
import com.github.byungtak.domain.common.Transformer
import com.github.byungtak.domain.entities.UserEntity
import io.reactivex.Observable

class GetFavoriteUser(
    transformer: Transformer<List<UserEntity>>,
    private val userRepository: UserRepository
): UseCase<List<UserEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<UserEntity>> {
        return userRepository.getFavoriteUsers()
    }

}