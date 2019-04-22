package com.github.byungtak.githubsearch.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.byungtak.githubsearch.R

fun FragmentActivity.replaceFragment(fragment: Fragment, tag: String = "", frameId: Int)
= supportFragmentManager
    .beginTransaction()
    .setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit)
    .replace(frameId, fragment)
    .commit()