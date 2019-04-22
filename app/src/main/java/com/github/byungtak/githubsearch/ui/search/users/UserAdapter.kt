package com.github.byungtak.githubsearch.ui.search.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.byungtak.githubsearch.R
import com.github.byungtak.githubsearch.data.model.User

internal class UserAdapter: RecyclerView.Adapter<UserViewHolder>() {

    private val users = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_user, parent, false)
        )
    }

    override fun getItemCount(): Int = users.count()

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun addUsers(users: List<User>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

}