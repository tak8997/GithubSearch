package com.github.byungtak.githubsearch.ui.search.users

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.byungtak.githubsearch.data.model.User
import com.github.byungtak.githubsearch.extension.onClick
import com.github.byungtak.githubsearch.extension.setImageWithGlide
import kotlinx.android.synthetic.main.item_user.view.*

internal class UserViewHolder(
    itemView: View,
    private val onFavoriteClickHandler: (User, Int) -> Unit
): RecyclerView.ViewHolder(itemView) {

    fun bind(user: User) {
        with(itemView) {
            img_user.setImageWithGlide(user.avatar_url)
            tv_username.text = user.login
            tv_userscore.text = user.score.toString()
            favorite_state.isSelected = user.isFavorite

            favorite_state.onClick {
                favorite_state.isSelected = !user.isFavorite
                user.isFavorite = !user.isFavorite

                onFavoriteClickHandler(user, adapterPosition)
            }
        }
    }

}
