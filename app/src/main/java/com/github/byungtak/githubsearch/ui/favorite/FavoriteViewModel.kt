package com.github.byungtak.githubsearch.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.byungtak.githubsearch.BaseViewModel
import com.github.byungtak.githubsearch.data.UserRepository
import com.github.byungtak.githubsearch.data.model.User

internal class FavoriteViewModel(private val searchRepository: UserRepository): BaseViewModel() {

    private val _removeUser = MutableLiveData<Pair<User, Int>>()
    val removeUser: LiveData<Pair<User, Int>> = _removeUser

    fun onFavoriteButtonClicked(user: User, adapterPosition: Int) {
        disposables.add(
            searchRepository
                .updateFavoriteUser(user)
                .subscribe({ _removeUser.value = Pair(user, adapterPosition) }) { it.printStackTrace() }
        )
    }

}