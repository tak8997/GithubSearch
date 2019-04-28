package com.github.byungtak.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class UserData(
    @SerializedName("login")
    val login: String,

    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("node_id")
    val node_id: String,

    @SerializedName("avatar_url")
    val avatar_url: String,

    @SerializedName("gravatar_id")
    val gravatar_id: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("html_url")
    val html_url: String,

    @SerializedName("followers_url")
    val followers_url: String,

    @SerializedName("subscriptions_url")
    val subscriptions_url: String,

    @SerializedName("organizations_url")
    val organizations_url: String,

    @SerializedName("repos_url")
    val repos_url: String,

    @SerializedName("received_events_url")
    val received_events_url: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("score")
    val score: Float,

    @SerializedName("isfavorite")
    var isFavorite: Boolean = false
)
