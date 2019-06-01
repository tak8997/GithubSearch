package com.github.byungtak.githubsearch.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.byungtak.githubsearch.entities.User
import com.github.byungtak.githubsearch.extension.onClick
import com.github.byungtak.githubsearch.extension.onTextChanged
import com.github.byungtak.githubsearch.ui.common.PaginationScrollListener
import com.github.byungtak.githubsearch.ui.common.UserAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel


internal class SearchFragment: Fragment() {

    companion object {
        const val TAG = "search"
        const val PAGE_START = 1

        fun newInstance() = SearchFragment()
    }

    private var currentPage = PAGE_START
    private var isLoading = false
    private var isLastPage = false

    private val linearLayoutManager = LinearLayoutManager(activity)
    private val viewModel by viewModel<SearchViewModel>()
    private val userAdapter by lazy {
        UserAdapter(viewModel::onFavoriteButtonClicked)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.github.byungtak.githubsearch.R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getFavoriteUsers()

        bindViewModel()

        setupRecycler()
        setupListeners()
    }

    fun removeUserFavorite(user: User) {
        userAdapter.removeUserFavorite(user)
    }

    private fun bindViewModel() {
        viewModel.run {
            users.observe(this@SearchFragment, Observer {
                userAdapter.setUsers(it)
            })

            addUsers.observe(this@SearchFragment, Observer {
                userAdapter.addUsers(it)

                swipe_refresh.isRefreshing = false
                this@SearchFragment.isLoading = false
            })

            lastQuery.observe(this@SearchFragment, Observer {
                userAdapter.lastQuery = it
            })

            searchBtnEnabled.observe(this@SearchFragment, Observer {
                btn_search.isEnabled = it
            })

            favoriteState.observe(this@SearchFragment, Observer {
                val message = if (it) "좋아요 목록에 추가하였습니다" else "좋아요 목록에서 제거하였습니다"
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            })

            throwable.observe(this@SearchFragment, Observer {
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            })

            favoriteUsers.observe(this@SearchFragment, Observer {
                userAdapter.saveFavoriteUser(it)
            })
        }
    }

    private fun setupRecycler() {
        with(user_recycler) {
            layoutManager = linearLayoutManager
            adapter = userAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupListeners() {
        et_search.onTextChanged { viewModel.onUserTextChanged(it) }

        btn_search.onClick {
            val query = et_search.text.toString()
            if (query != userAdapter.lastQuery) {
                viewModel.searchUser(query)
            }
        }

        user_recycler.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override var isLastPage: Boolean = this@SearchFragment.isLastPage

            override var isLoading: Boolean = this@SearchFragment.isLoading

            override fun loadMoreItems() {
                this@SearchFragment.isLoading = true
                currentPage += 1

                viewModel.searchUser(userAdapter.lastQuery, currentPage)
            }
        })

        swipe_refresh.setOnRefreshListener {
            swipe_refresh.isRefreshing = false
        }

        btn_clear.onClick {
            et_search.text?.clear()
        }
    }

}