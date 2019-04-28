package com.github.byungtak.githubsearch.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.byungtak.domain.common.Mapper
import com.github.byungtak.domain.entities.UserEntity
import com.github.byungtak.domain.usecases.*
import com.github.byungtak.githubsearch.BaseViewModel
import com.github.byungtak.githubsearch.entities.User
import com.github.byungtak.githubsearch.util.isValidSearch

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

    private val _showFavoriteState = MutableLiveData<Boolean>()
    val showFavoriteState: LiveData<Boolean> = _showFavoriteState

    private val _throwable = MutableLiveData<Throwable>()
    val throwable: LiveData<Throwable> = _throwable

    private val _favoriteUsers = MutableLiveData<List<User>>()
    val favoriteUsers: LiveData<List<User>> = _favoriteUsers

    private val userEntities = mutableListOf<UserEntity>()

    init {
//        removeAllFavoriteUser.observable()
//            .subscribe {
//                Log.d("MY_LOG", "clear")
//            }
        getFavoriteUsers()
    }

    fun onUserTextChanged(query: String) {
        _searchBtnEnabled.value = isValidSearch(query)
    }

    fun searchUser(query: String, currentPage: Int = 1) {
        if (currentPage == 1) {
            userEntities.clear()
        }

        if (isValidSearch(query)) {
            disposables.add(
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
            )
        }
    }

    fun onFavoriteButtonClicked(user: User, adapterPosition: Int) {
        if (user.isFavorite) {
            disposables.add(
                saveFavoriteUser
                    .saveUser(userEntities[adapterPosition])
                    .subscribe({
                        _showFavoriteState.value = user.isFavorite
                    }, { _throwable.value = it })
            )
        } else {
            disposables.add(
                removeFavoriteUser
                    .removeUser(userEntities[adapterPosition])
                    .subscribe({
                        _showFavoriteState.value = user.isFavorite
                    }, { _throwable.value = it })
            )
        }
    }

    private fun getFavoriteUsers() {
        disposables.add(
            getFavoriteUser
                .observable()
                .flatMap { entitiyUserMapper.observable(it) }
                .subscribe(_favoriteUsers::setValue) {
                    it.printStackTrace()
                    _throwable.value = it }
        )
    }

}