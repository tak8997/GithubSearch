package com.github.byungtak.githubsearch.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.byungtak.githubsearch.BaseViewModel
import com.github.byungtak.githubsearch.data.UserRepository
import com.github.byungtak.githubsearch.data.model.User
import com.github.byungtak.githubsearch.util.isValidSearch

internal class SearchViewModel(private val searchRepository: UserRepository): BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _addUsers = MutableLiveData<List<User>>()
    val addUsers: LiveData<List<User>> = _addUsers

    private val _lastQuery = MutableLiveData<String>()
    val lastQuery: LiveData<String> = _lastQuery

    private val _searchBtnEnabled = MutableLiveData<Boolean>()
    val searchBtnEnabled: LiveData<Boolean> = _searchBtnEnabled

    private val _showFavoriteState = MutableLiveData<User>()
    val showFavoriteState: LiveData<User> = _showFavoriteState

    fun onUserTextChanged(query: String) {
        _searchBtnEnabled.value = isValidSearch(query)
    }

    fun searchUser(query: String, currentPage: Int = 1) {
        if (isValidSearch(query)) {
            disposables.add(
                searchRepository
                    .searchUser(query, currentPage)
                    .subscribe({
                        if (currentPage == 1) {
                            _users.value = it
                        } else {
                            _addUsers.value = it
                        }

                        _lastQuery.value = query
                    }) { it.printStackTrace() }
            )
        }
    }

    fun onFavoriteButtonClicked(user: User, adapterPosition: Int) {
        disposables.add(
            searchRepository
                .updateFavoriteUser(user)
                .subscribe( {
                    _showFavoriteState.value = user
                } , { it.printStackTrace() })
        )
    }

}