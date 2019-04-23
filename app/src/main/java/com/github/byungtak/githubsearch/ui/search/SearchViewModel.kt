package com.github.byungtak.githubsearch.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.byungtak.githubsearch.BaseViewModel
import com.github.byungtak.githubsearch.data.SearchRepository
import com.github.byungtak.githubsearch.data.model.User
import com.github.byungtak.githubsearch.util.isValidUser

internal class SearchViewModel(private val searchRepository: SearchRepository): BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _searchBtnEnabled = MutableLiveData<Boolean>()
    val searchBtnEnabled: LiveData<Boolean> = _searchBtnEnabled

    fun onUserTextChanged(userText: String) {
        _searchBtnEnabled.value = isValidUser(userText)
    }

    fun onUserSearchClicked(userText: String) {
        if (isValidUser(userText)) {
            disposables.add(
                searchRepository
                    .searchUser(userText)
                    .subscribe(_users::setValue) {it.printStackTrace()}
            )
        }
    }

    fun onFavoriteButtonClicked(user: User) {
        disposables.add(
            searchRepository
                .addFavoriteUser(user)
                .subscribe()
        )
        Log.d("MY_LOG", user.isFavorite.toString())
    }

}