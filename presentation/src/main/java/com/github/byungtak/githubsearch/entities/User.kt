package com.github.byungtak.githubsearch.entities

data class User(
    val id: Int,
    val username: String,
    val userimage: String,
    val score: Float,
    var isFavorite: Boolean = false
)
