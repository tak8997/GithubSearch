package com.github.byungtak.domain.common

import com.github.byungtak.domain.entities.UserEntity

class DomainTestUtils {

    companion object {
        fun getTestUserEntity(id: Int): UserEntity {
            return UserEntity(
                login = "github$id",
                id = id,
                node_id = "",
                avatar_url = "",
                gravatar_id = "",
                url = "",
                html_url = "",
                followers_url = "",
                subscriptions_url = "",
                organizations_url = "",
                repos_url = "",
                received_events_url = "",
                type = "",
                score = 0F,
                isFavorite = false
            )
        }

        fun getTestUserFavoriteStateEntity(id: Int): UserEntity {
            return UserEntity(
                login = "github$id",
                id = id,
                node_id = "",
                avatar_url = "",
                gravatar_id = "",
                url = "",
                html_url = "",
                followers_url = "",
                subscriptions_url = "",
                organizations_url = "",
                repos_url = "",
                received_events_url = "",
                type = "",
                score = 0F,
                isFavorite = true
            )
        }

        fun generateUserEntityList(): List<UserEntity> {
            return (0..4).map { getTestUserEntity(it) }
        }

        fun generateUserStateFavoriteEntityList(): List<UserEntity> {
            return (0..4).map { getTestUserFavoriteStateEntity(it) }
        }
    }
}