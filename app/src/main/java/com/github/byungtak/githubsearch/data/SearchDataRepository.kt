package com.github.byungtak.githubsearch.data

import com.github.byungtak.githubsearch.data.model.User
import com.github.byungtak.githubsearch.data.remote.SearchRemoteRepository
import com.github.byungtak.githubsearch.util.SchedulersProvider
import io.reactivex.Single

internal class SearchDataRepository(
    private val searchRemoteRepository: SearchRemoteRepository,
    private val schedulersProvider: SchedulersProvider
): SearchRepository {

    override fun searchUser(userText: String): Single<List<User>> {
        return searchRemoteRepository
            .searchUser(userText)
            .subscribeOn(schedulersProvider.io())
            .observeOn(schedulersProvider.ui())
    }

}