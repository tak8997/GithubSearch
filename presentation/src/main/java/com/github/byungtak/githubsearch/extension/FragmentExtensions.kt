package com.github.byungtak.githubsearch.extension

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.byungtak.githubsearch.R

fun FragmentActivity.replaceFragment(fragment: Fragment, tag: String = "", frameId: Int) {
    supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit)
        .replace(frameId, fragment)
        .commit()
}

fun AppCompatActivity.addFragment(@IdRes containerViewId: Int, fragment: Fragment, tag: String) {
    supportFragmentManager.beginTransaction()
        .add(containerViewId, fragment, tag)
        .commitNow()
}

fun AppCompatActivity.showFragment(tag: String) {
    val fragment = supportFragmentManager.findFragmentByTag(tag)

    fragment?.let {
        supportFragmentManager.beginTransaction()
        .show(it)
        .commitNow()
    }
}

fun AppCompatActivity.hideFragment(tag: String) {
    val fragment = supportFragmentManager.findFragmentByTag(tag)

    fragment?.let {
        supportFragmentManager.beginTransaction()
        .hide(it)
        .commitNow()
    }
}
