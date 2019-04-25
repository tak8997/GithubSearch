package com.github.byungtak.githubsearch.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.byungtak.githubsearch.R
import com.github.byungtak.githubsearch.extension.onClick
import com.github.byungtak.githubsearch.extension.onTextChanged
import com.github.byungtak.githubsearch.ui.search.users.UserAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class SearchFragment: Fragment() {

    companion object {
        const val TAG = "search"

        fun newInstance() = SearchFragment()
    }

    private val viewModel by viewModel<SearchViewModel>()
    private val userAdapter by lazy {
        UserAdapter(viewModel::onFavoriteButtonClicked)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        bindViewModel()

        setupRecycler()
        setupListeners()
    }

    private fun bindViewModel() {
        with(viewModel) {
            users.observe(this@SearchFragment, Observer {
                userAdapter.addUsers(it)
            })

            searchBtnEnabled.observe(this@SearchFragment, Observer {
                btn_search.isEnabled = it
            })

            showFavoriteState.observe(this@SearchFragment, Observer {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun setupRecycler() {
        with(user_recycler) {
            layoutManager = LinearLayoutManager(this@SearchFragment.context)
            adapter = userAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupListeners() {
        et_search.onTextChanged { viewModel.onUserTextChanged(it) }
        btn_search.onClick {
            viewModel.onUserSearchClicked(et_search.text.toString())
        }
    }

}