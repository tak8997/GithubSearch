package com.github.byungtak.githubsearch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.byungtak.githubsearch.R
import com.github.byungtak.githubsearch.entities.User
import com.github.byungtak.githubsearch.extension.addFragment
import com.github.byungtak.githubsearch.extension.hideFragment
import com.github.byungtak.githubsearch.extension.showFragment
import com.github.byungtak.githubsearch.ui.common.OnUserFavoriteClickListener
import com.github.byungtak.githubsearch.ui.favorite.FavoriteFragment
import com.github.byungtak.githubsearch.ui.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

internal class MainActivity : AppCompatActivity() {

    private val searchFragment = SearchFragment.newInstance()
    private val favoriteFragment = FavoriteFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupFragments()
        setupListeners()
    }

    private fun setupListeners() {
        favoriteFragment.setOnUserFavoriteClickedListener(object : OnUserFavoriteClickListener {
            override fun onFavoriteUserClicked(user: User, currentfragment: String?) {
                searchFragment.removeUserFavorite(user)
            }
        })

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            hideFragment(SearchFragment.TAG)
            hideFragment(FavoriteFragment.TAG)

            when (item.itemId) {
                R.id.navigation_search -> {
                    showFragment(SearchFragment.TAG)
                    true
                }
                R.id.navigation_favorite -> {
                    favoriteFragment.getFavoriteUser()
                    showFragment(FavoriteFragment.TAG)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupFragments() {
        addFragment(R.id.container, searchFragment, SearchFragment.TAG)
        addFragment(R.id.container, favoriteFragment, FavoriteFragment.TAG)

        hideFragment(FavoriteFragment.TAG)
    }

}
