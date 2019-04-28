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

    fun setupFavoriteUsers(users: List<User>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun setUsers(users: List<User>) {
        setFavoriteUser(users)

        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    fun addUsers(users: List<User>) {
        setFavoriteUser(users)

        for (user in users) {
            this.users.add(user)
            notifyItemInserted(this.users.size - 1)
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

    private fun setFavoriteUser(users: List<User>) {
        users.forEach { user ->
            favoriteUsers.forEach { favoriteUser ->
                if (user.id == favoriteUser.id) {
                    user.isFavorite = true
                }
            }
        }
    }

}