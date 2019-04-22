package com.github.byungtak.githubsearch.data.model

internal data class UserModel(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<User>
)
