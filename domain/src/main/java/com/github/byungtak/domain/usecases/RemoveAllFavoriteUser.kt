package com.github.byungtak.domain.usecases

import com.github.byungtak.domain.UserRepository
import com.github.byungtak.domain.common.Transformer
import io.reactivex.Completable
import io.reactivex.Observable

class RemoveAllFavoriteUser(
    transformer: Transformer<Completable>,
    private val userRepository: UserRepository
): UseCase<Completable>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<Completable> {
        return Observable.just(userRepository.removeAllUsers())
    }

}