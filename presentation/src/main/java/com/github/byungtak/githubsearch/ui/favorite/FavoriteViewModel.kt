package com.github.byungtak.githubsearch.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.byungtak.domain.common.Mapper
import com.github.byungtak.domain.entities.UserEntity
import com.github.byungtak.domain.usecases.GetFavoriteUser
import com.github.byungtak.domain.usecases.RemoveFavoriteUser
import com.github.byungtak.githubsearch.BaseViewModel
import com.github.byungtak.githubsearch.entities.User

internal class FavoriteViewModel(
    private val getFavoriteUser: GetFavoriteUser,
    private val removeFavoriteUser: RemoveFavoriteUser,
    private val entitiyUserMapper: Mapper<UserEntity, User>
): BaseViewModel() {

    private val _removeUser = MutableLiveData<Pair<User, Int>>()
    val removeUser: LiveData<Pair<User, Int>> = _removeUser

    private val _favoriteUsers = MutableLiveData<List<User>>()
    val favoriteUsers: LiveData<List<User>> = _favoriteUsers

    private val _throwable = MutableLiveData<Throwable>()
    val throwable: LiveData<Throwable> = _throwable

    private lateinit var userEntities: List<UserEntity>

    fun onFavoriteButtonClicked(user: User, adapterPosition: Int) {
        disposables.add(
            removeFavoriteUser
                .removeUser(userEntities[adapterPosition])
                .subscribe({
                    _removeUser.value = Pair(user, adapterPosition)
                }, { _throwable.value = it })
        )
    }

    fun getFavoriteUsers() {
        disposables.add(
            getFavoriteUser
                .observable()
                .map {
                    userEntities = it
                    it
                }
                .flatMap { entitiyUserMapper.observable(it) }
                .subscribe(_favoriteUsers::setValue) { _throwable.value = it }
        )
    }

}