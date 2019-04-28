package com.github.byungtak.githubsearch.util


const val MIN_QUERY_LENGTH = 1

fun isValidSearch(query: String) = query.length >= MIN_QUERY_LENGTH