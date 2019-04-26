package com.github.byungtak.githubsearch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.github.byungtak.githubsearch.R
import com.github.byungtak.githubsearch.data.model.User
import com.github.byungtak.githubsearch.extension.addFragment
import com.github.byungtak.githubsearch.extension.hideFragment
import com.github.byungtak.githubsearch.extension.showFragment
import com.github.byungtak.githubsearch.ui.favorite.FavoriteFragment
import com.github.byungtak.githubsearch.ui.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class MainActivity : AppCompatActivity(), OnUserFavoriteClickListener {

    private val viewModel by viewModel<MainViewModel>()

    private val searchFragment = SearchFragment.newInstance()
    private val favoriteFragment = FavoriteFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViewModel()

        setupFragments()
        setupListeners()
    }

    override fun onFavoriteUserClicked(user: User, currentfragment: String?) {
        if (currentfragment == SearchFragment.TAG) {
            favoriteFragment.updateUserFavorite(user)
        } else {
            searchFragment.removeUserFavorite(user)
        }
    }

    private fun setupListeners() {
        searchFragment.setOnUserFavoriteClickedListener(this)
        favoriteFragment.setOnUserFavoriteClickedListener(this)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            hideFragment(SearchFragment.TAG)
            hideFragment(FavoriteFragment.TAG)

            when (item.itemId) {
                R.id.navigation_search -> {
                    showFragment(SearchFragment.TAG)
                    true
                }
                R.id.navigation_favorite -> {
                    showFragment(FavoriteFragment.TAG)
                    true
                }
                else -> false
            }
        }
    }

    private fun bindViewModel() {
        viewModel.run {
            favoriteUsers.observe(this@MainActivity, Observer {
                searchFragment.setupFavoriteUsers(it)
                favoriteFragment.setupFavoriteUsers(it)
            })
        }
    }

    private fun setupFragments() {
        addFragment(R.id.container, searchFragment, SearchFragment.TAG)
        addFragment(R.id.container, favoriteFragment, FavoriteFragment.TAG)

        hideFragment(FavoriteFragment.TAG)
    }

}
