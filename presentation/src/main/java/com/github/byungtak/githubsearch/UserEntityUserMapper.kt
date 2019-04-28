package com.github.byungtak.githubsearch

import com.github.byungtak.domain.common.Mapper
import com.github.byungtak.domain.entities.UserEntity
import com.github.byungtak.githubsearch.data.model.User


class UserEntityUserMapper: Mapper<UserEntity, User>() {

    override fun mapFrom(from: UserEntity): User {
        return User(
            id = from.id,
            username = from.login,
            userimage = from.avatar_url,
            score = from.score,
            isFavorite = from.isFavorite
        )
    }

}