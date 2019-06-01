package com.github.byungtak.githubsearch.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.byungtak.domain.common.Mapper
import com.github.byungtak.domain.entities.UserEntity
import com.github.byungtak.domain.usecases.*
import com.github.byungtak.githubsearch.BaseViewModel
import com.github.byungtak.githubsearch.entities.User
import com.github.byungtak.githubsearch.util.isValidSearch
import io.reactivex.rxkotlin.addTo

internal class SearchViewModel(
    private val searchUser: SearchUser,
    private val getFavoriteUser: GetFavoriteUser,
    private val saveFavoriteUser: SaveFavoriteUser,
    private val removeFavoriteUser: RemoveFavoriteUser,
    private val removeAllFavoriteUser: RemoveAllFavoriteUser,
    private val entitiyUserMapper: Mapper<UserEntity, User>
): BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    private val _addUsers = MutableLiveData<List<User>>()
    val addUsers: LiveData<List<User>> = _addUsers

    private val _lastQuery = MutableLiveData<String>()
    val lastQuery: LiveData<String> = _lastQuery

    private val _searchBtnEnabled = MutableLiveData<Boolean>()
    val searchBtnEnabled: LiveData<Boolean> = _searchBtnEnabled

    private val _favoriteState = MutableLiveData<Boolean>()
    val favoriteState: LiveData<Boolean> = _favoriteState

    private val _throwable = MutableLiveData<Throwable>()
    val throwable: LiveData<Throwable> = _throwable

    private val _favoriteUsers = MutableLiveData<List<User>>()
    val favoriteUsers: LiveData<List<User>> = _favoriteUsers

    val userEntities = mutableListOf<UserEntity>()

    fun onUserTextChanged(query: String) {
        _searchBtnEnabled.value = isValidSearch(query)
    }

    fun searchUser(query: String, currentPage: Int = 1) {
        if (currentPage == 1) {
            userEntities.clear()
        }

        if (isValidSearch(query)) {
            searchUser
                .searchUser(query, currentPage)
                .map {
                    userEntities.addAll(it)
                    it
                }
                .flatMap { entitiyUserMapper.observable(it) }
                .subscribe({
                    if (currentPage == 1) {
                        _users.value = it
                    } else {
                        _addUsers.value = it
                    }

                    _lastQuery.value = query
                }) { _throwable.value = it }
                .addTo(disposables)
        }
    }

    fun onFavoriteButtonClicked(user: User, position: Int) {
        if (user.isFavorite) {
            saveFavoriteUser
                .saveUser(userEntities[position])
                .subscribe({
                    Log.d("MY_LOG", user.isFavorite.toString())
                    _favoriteState.value = user.isFavorite
                } ,{ _throwable.value = it })
                .addTo(disposables)
        } else {
            removeFavoriteUser
                .removeUser(userEntities[position])
                .subscribe({
                    _favoriteState.value = user.isFavorite
                }, { _throwable.value = it })
                .addTo(disposables)
        }
    }

    fun getFavoriteUsers() {
        getFavoriteUser
            .observable()
            .flatMap { entitiyUserMapper.observable(it) }
            .subscribe(_favoriteUsers::setValue) {
                it.printStackTrace()
                _throwable.value = it }
            .addTo(disposables)
    }

}