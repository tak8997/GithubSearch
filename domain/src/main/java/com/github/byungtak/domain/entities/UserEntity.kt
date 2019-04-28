package com.github.byungtak.domain.entities


data class UserEntity(
    val id: Int,
    val username: String,
    val userimage: String,
    val score: Float,
    var isFavorite: Boolean = false
)
