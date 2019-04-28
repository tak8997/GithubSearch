package com.github.byungtak.data.mappers

import com.github.byungtak.data.entities.UserData
import com.github.byungtak.domain.common.Mapper
import com.github.byungtak.domain.entities.UserEntity

class UserDataEntityMapper: Mapper<UserData, UserEntity>() {

    override fun mapFrom(from: UserData): UserEntity {
        return UserEntity(
            id = from.id,
            username = from.login,
            userimage = from.avatar_url,
            score = from.score,
            isFavorite = from.isFavorite
        )
    }

}
