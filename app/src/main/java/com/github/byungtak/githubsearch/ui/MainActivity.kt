package com.github.byungtak.githubsearch.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.github.byungtak.githubsearch.R
import com.github.byungtak.githubsearch.extension.addFragment
import com.github.byungtak.githubsearch.extension.hideFragment
import com.github.byungtak.githubsearch.extension.showFragment
import com.github.byungtak.githubsearch.ui.favorite.FavoriteFragment
import com.github.byungtak.githubsearch.ui.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupFragments()
        setupListeners()
    }

    private fun setupFragments() {
        addFragment(R.id.container, SearchFragment.newInstance(), SearchFragment.TAG)
        addFragment(R.id.container, FavoriteFragment.newInstance(), FavoriteFragment.TAG)

        hideFragment(FavoriteFragment.TAG)
    }

    private fun setupListeners() {
        bottom_navigation.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                hideFragment(SearchFragment.TAG)
                hideFragment(FavoriteFragment.TAG)

                return when (item.itemId) {
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
        })
    }

}
