package com.github.byungtak.githubsearch.ui.common

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.byungtak.githubsearch.entities.User
import com.github.byungtak.githubsearch.extension.onClick
import com.github.byungtak.githubsearch.extension.setImageWithGlide
import kotlinx.android.synthetic.main.item_user.view.*

internal class UserViewHolder(
    itemView: View,
    private val onFavoriteClickHandler: (User, Int) -> Unit
): RecyclerView.ViewHolder(itemView) {

    fun bind(user: User) {
        with(itemView) {
            img_user.setImageWithGlide(user.userimage)
            tv_username.text = user.username
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
