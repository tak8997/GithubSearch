package com.github.byungtak.data.repositories

import android.content.Context
import com.github.byungtak.data.db.UsersDatabase
import com.github.byungtak.data.entities.UserData
import com.github.byungtak.domain.UserLocalRepository
import com.github.byungtak.domain.common.Mapper
import com.github.byungtak.domain.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable

class UserLocalDataRepository(
    context: Context,
    private val userEntityToDataMapper: Mapper<UserEntity, UserData>,
    private val userDataToEntityMapper: Mapper<UserData, UserEntity>
): UserLocalRepository {

    private val userDao = UsersDatabase.getInstance(context).userDao()

    override fun saveFavoriteUser(user: UserEntity): Completable {
        userDao.saveFavoriteUser(userEntityToDataMapper.mapFrom(user))
        return Completable.complete()
    }


    override fun removeFavoriteUser(userEntity: UserEntity): Completable {
        userDao.removeFavoriteUser(userEntityToDataMapper.mapFrom(userEntity))
        return Completable.complete()
    }


    override fun getFavoriteUsers(): Observable<List<UserEntity>> {
        return Observable.fromCallable {
            userDao.getFavoriteUsers().map { userDataToEntityMapper.mapFrom(it) }
        }
    }

    override fun removeAllUsers(): Completable = userDao.removeAllFavoriteUsers()

}