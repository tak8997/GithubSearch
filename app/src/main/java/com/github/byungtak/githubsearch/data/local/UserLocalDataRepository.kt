package com.github.byungtak.githubsearch.data.local

import com.github.byungtak.githubsearch.App
import com.github.byungtak.githubsearch.data.model.User
import io.reactivex.Completable

internal class UserLocalDataRepository : UserLocalRepository {

    private val userDao = UsersDatabase.getInstance(App.instance).userDao()

    override fun addFavoriteUser(user: User): Completable = userDao.insertFavoriteUser(user)

    override fun deleteFavoriteUser(userid: String): Completable = userDao.deleteFavoriteUser(userid)

}