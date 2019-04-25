package com.github.byungtak.githubsearch.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
internal data class User(
    @ColumnInfo(name = "username")
    val login: String,

    @PrimaryKey
    @ColumnInfo(name = "userid")
    val id: Int,

    @ColumnInfo(name = "nodeid")
    val node_id: String,

    @ColumnInfo(name = "userimage")
    val avatar_url: String,

    @ColumnInfo(name = "gravatarid")
    val gravatar_id: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "html_url")
    val html_url: String,

    @ColumnInfo(name = "followers_url")
    val followers_url: String,

    @ColumnInfo(name = "subscriptions_url")
    val subscriptions_url: String,

    @ColumnInfo(name = "organizations_url")
    val organizations_url: String,

    @ColumnInfo(name = "repos_url")
    val repos_url: String,

    @ColumnInfo(name = "received_events_url")
    val received_events_url: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "score")
    val score: Float,

    @ColumnInfo(name = "isfavorite")
    var isFavorite: Boolean = false
)
