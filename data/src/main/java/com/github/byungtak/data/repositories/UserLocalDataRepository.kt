package com.github.byungtak.data.repositories

import android.content.Context
import com.github.byungtak.data.db.UsersDatabase
import com.github.byungtak.domain.UserLocalRepository
import com.github.byungtak.domain.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable

class UserLocalDataRepository(context: Context): UserLocalRepository {

    private val userDao = UsersDatabase.getInstance(context).userDao()

    override fun saveFavoriteUser(user: UserEntity): Completable = userDao.insertFavoriteUser(user)

    override fun removeFavoriteUser(userid: String): Completable = userDao.deleteFavoriteUser(userid)

    override fun getFavoriteUsers(): Observable<List<UserEntity>> = userDao.getFavoriteUsers()
}