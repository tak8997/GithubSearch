package com.github.byungtak.githubsearch.ui.common

import com.github.byungtak.githubsearch.data.model.User

internal interface OnUserFavoriteClickListener {
    fun onFavoriteUserClicked(user: User, currentfragment: String?)
}
