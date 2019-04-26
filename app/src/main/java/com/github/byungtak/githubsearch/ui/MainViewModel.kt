package com.github.byungtak.githubsearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.byungtak.githubsearch.BaseViewModel
import com.github.byungtak.githubsearch.data.UserRepository
import com.github.byungtak.githubsearch.data.model.User

internal class MainViewModel(private val searchRepository: UserRepository): BaseViewModel() {

    private val _favoriteUsers = MutableLiveData<List<User>>()
    val favoriteUsers: LiveData<List<User>> = _favoriteUsers

    init {
        getFavoriteUsers()
    }

    private fun getFavoriteUsers() {
        disposables.add(
            searchRepository
                .getFavoriteUsers()
                .subscribe(_favoriteUsers::setValue) { it.printStackTrace() }
        )
    }

}