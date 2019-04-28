package com.github.byungtak.githubsearch.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.byungtak.githubsearch.R
import com.github.byungtak.githubsearch.ui.common.OnUserFavoriteClickListener
import com.github.byungtak.githubsearch.ui.common.UserAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class FavoriteFragment: Fragment() {

    companion object {
        const val TAG = "favorite"

        fun newInstance() = FavoriteFragment()
    }

    private var favoriteUserListener: OnUserFavoriteClickListener? = null

    private val viewModel by viewModel<FavoriteViewModel>()
    private val userAdapter by lazy {
        UserAdapter(viewModel::onFavoriteButtonClicked)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViewModel()

        setupRecycler()
    }

    fun getFavoriteUser() = viewModel.getFavoriteUsers()

    fun setOnUserFavoriteClickedListener(listener: OnUserFavoriteClickListener?) {
        favoriteUserListener = listener
    }

    private fun bindViewModel() {
        viewModel.run {
            removeUser.observe(this@FavoriteFragment, Observer {
                val user = it.first
                val adapterPosition = it.second

                userAdapter.removeUser(adapterPosition)

                favoriteUserListener?.onFavoriteUserClicked(user, tag)
            })

            favoriteUsers.observe(this@FavoriteFragment, Observer {
                userAdapter.setFavoriteUsers(it)
            })

            throwable.observe(this@FavoriteFragment, Observer {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun setupRecycler() {
        with(user_recycler) {
            layoutManager = LinearLayoutManager(this@FavoriteFragment.context)
            adapter = userAdapter
            setHasFixedSize(true)
        }
    }

}