package com.github.byungtak.githubsearch.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.byungtak.githubsearch.BaseViewModel
import com.github.byungtak.domain.UserRepository
import com.github.byungtak.githubsearch.data.model.User

internal class FavoriteViewModel(private val searchRepository: UserRepository): BaseViewModel() {

    private val _removeUser = MutableLiveData<Pair<User, Int>>()
    val removeUser: LiveData<Pair<User, Int>> = _removeUser

    private val _favoriteUsers = MutableLiveData<List<User>>()
    val favoriteUsers: LiveData<List<User>> = _favoriteUsers

    private val _throwable = MutableLiveData<Throwable>()
    val throwable: LiveData<Throwable> = _throwable

    fun onFavoriteButtonClicked(user: User, adapterPosition: Int) {
        disposables.add(
            searchRepository
                .updateFavoriteUser(user)
                .subscribe({ _removeUser.value = Pair(user, adapterPosition) }) { _throwable.value = it }
        )
    }

    fun getFavoriteUsers() {
        disposables.add(
            searchRepository
                .getFavoriteUsers()
                .subscribe(_favoriteUsers::setValue) { _throwable.value = it }
        )
    }

}