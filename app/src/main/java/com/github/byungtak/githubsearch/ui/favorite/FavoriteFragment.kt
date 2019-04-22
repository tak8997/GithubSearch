package com.github.byungtak.githubsearch.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.byungtak.githubsearch.R

internal class FavoriteFragment: Fragment() {

    companion object {
        const val TAG = "favorite"

        fun newInstance() = FavoriteFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }
}