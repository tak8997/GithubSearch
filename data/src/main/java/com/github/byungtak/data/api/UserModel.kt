package com.github.byungtak.data.api

import com.github.byungtak.data.entities.UserData

data class UserModel(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<UserData>
)
