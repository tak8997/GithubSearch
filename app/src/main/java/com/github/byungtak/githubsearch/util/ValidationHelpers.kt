package com.github.byungtak.githubsearch.util


const val MIN_USER_LENGTH = 1

fun isValidUser(userText: String) = userText.length >= MIN_USER_LENGTH