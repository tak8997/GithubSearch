package com.github.byungtak.domain.usecases

import com.github.byungtak.domain.UserRepository
import com.github.byungtak.domain.common.Transformer
import com.github.byungtak.domain.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable

class SaveFavoriteUser(
    transformer: Transformer<Completable>,
    private val userRepository: UserRepository
): UseCase<Completable>(transformer) {

    companion object {
        private const val PARAM_USER_ENTITY = "param:userEntity"
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Completable> {
        val userEntity = data?.get(PARAM_USER_ENTITY)

        userEntity?.let {
            return Observable.fromCallable {
                val userEntity = it as UserEntity
                return@fromCallable userRepository.saveFavoriteUser(userEntity)
            }
        }?: return Observable.error({ IllegalArgumentException("MovieEntity must be provided.") })

    }

    fun saveUser(userEntity: UserEntity): Observable<Completable> {
        userEntity.isFavorite = true

        val data = HashMap<String, UserEntity>()
        data[PARAM_USER_ENTITY] = userEntity
        return observable(data)
    }

}