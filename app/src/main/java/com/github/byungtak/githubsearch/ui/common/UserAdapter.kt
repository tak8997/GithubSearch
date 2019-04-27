package com.github.byungtak.githubsearch.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.byungtak.githubsearch.R
import com.github.byungtak.githubsearch.data.model.User

internal class UserAdapter(private val onFavoriteClickHandler: (User, Int) -> Unit): RecyclerView.Adapter<UserViewHolder>() {

    private val users = mutableListOf<User>()
    private val favoriteUsers = mutableListOf<User>()

    var lastQuery = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)

        return UserViewHolder(view, onFavoriteClickHandler)
    }

    override fun getItemCount(): Int = users.count()

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun setupUserFavorite(users: List<User>) {
        favoriteUsers.addAll(users)
    }

    fun setUsers(users: List<User>) {
        
        users.forEach { user ->
            favoriteUsers.forEach { favoriteUser ->
                if (user.id == favoriteUser.id) {
                    user.isFavorite = true
                }
            }
        }

        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun addUsers(users: List<User>) {
        users.forEach { user ->
            favoriteUsers.forEach { favoriteUser ->
                if (user.id == favoriteUser.id) {
                    user.isFavorite = true
                }
            }
        }

        for (user in users) {
            this.users.add(user)
            notifyItemInserted(this.users.size - 1)
        }
    }



    fun addFavoriteUsers(users: List<User>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun updateUserFavorite(user: User) {
        if (user.isFavorite) {
            users.add(user)
            notifyDataSetChanged()
        } else {
            users.remove(user)
            notifyDataSetChanged()
        }
    }

    fun removeUser(adapterPosition: Int) {
        users.removeAt(adapterPosition)
        notifyItemRemoved(adapterPosition)
    }

    fun removeUserFavorite(user: User) {
        var adapterPosition: Int? = null
        for (i in 0 until users.size) {
            if (users[i].id == user.id) {
                users[i].isFavorite = false
                adapterPosition = i
                break
            }
        }

        adapterPosition?.let {
            notifyItemChanged(it)
        }
    }

}