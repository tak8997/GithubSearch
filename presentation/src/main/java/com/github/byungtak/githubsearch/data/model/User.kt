package com.github.byungtak.githubsearch.data.model

data class User(
    val id: Int,
    val username: String,
    val userimage: String,
    val score: Float,
    var isFavorite: Boolean = false
)
