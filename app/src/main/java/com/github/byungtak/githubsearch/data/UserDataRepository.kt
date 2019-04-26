package com.github.byungtak.githubsearch.data

import com.github.byungtak.githubsearch.data.local.UserLocalRepository
import com.github.byungtak.githubsearch.data.model.User
import com.github.byungtak.githubsearch.data.remote.UserRemoteRepository
import com.github.byungtak.githubsearch.util.SchedulersProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

internal class UserDataRepository(
    private val userLocalRepository: UserLocalRepository,
    private val searchRemoteRepository: UserRemoteRepository,
    private val schedulersProvider: SchedulersProvider
): UserRepository {

    override fun searchUser(userText: String): Single<List<User>> {
        return searchRemoteRepository
            .searchUser(userText)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
    }

    override fun updateFavoriteUser(user: User): Completable {
        return if (user.isFavorite) {
            userLocalRepository
                .addFavoriteUser(user)
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
        } else {
            userLocalRepository
                .removeFavoriteUser(user.id.toString())
                .subscribeOn(schedulersProvider.io())
                .observeOn(schedulersProvider.ui())
        }
    }

    override fun getFavoriteUsers(): Flowable<List<User>> {
        return userLocalRepository
            .getFavoriteUsers()
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
    }

}