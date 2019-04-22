package com.github.byungtak.githubsearch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.byungtak.githubsearch.R
import com.github.byungtak.githubsearch.extension.replaceFragment
import com.github.byungtak.githubsearch.ui.favorite.FavoriteFragment
import com.github.byungtak.githubsearch.ui.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<MainViewModel>()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                replaceFragment(SearchFragment.newInstance(), SearchFragment.TAG, R.id.container)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                replaceFragment(FavoriteFragment.newInstance(), FavoriteFragment.TAG, R.id.container)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(SearchFragment.newInstance(), SearchFragment.TAG, R.id.container)

        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
