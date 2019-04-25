package com.github.byungtak.githubsearch.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.byungtak.githubsearch.BaseViewModel
import com.github.byungtak.githubsearch.data.UserRepository
import com.github.byungtak.githubsearch.data.model.User
import com.github.byungtak.githubsearch.util.isValidUser

internal class SearchViewModel(private val searchRepository: UserRepository): BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _searchBtnEnabled = MutableLiveData<Boolean>()
    val searchBtnEnabled: LiveData<Boolean> = _searchBtnEnabled

    private val _showFavoriteState = MutableLiveData<String>()
    val showFavoriteState: LiveData<String> = _showFavoriteState

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
                .updateFavoriteUser(user)
                .subscribe( {
                    val message = if (user.isFavorite) "좋아요 목록에 추가하였습니다" else "좋아요 목록에서 제거하였습니다"
                    _showFavoriteState.value = message
                } , { it.printStackTrace() } )
        )
    }

}