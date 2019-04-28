package com.github.byungtak.data.mappers

import com.github.byungtak.data.entities.UserData
import com.github.byungtak.domain.common.Mapper
import com.github.byungtak.domain.entities.UserEntity

class UserEntityToDataMapper: Mapper<UserEntity, UserData>() {

    override fun mapFrom(from: UserEntity): UserData {
        return UserData(
            login = from.login,
            id = from.id,
            node_id = from.node_id,
            avatar_url = from.avatar_url,
            gravatar_id = from.gravatar_id,
            url = from.url,
            html_url = from.html_url,
            followers_url = from.followers_url,
            subscriptions_url = from.subscriptions_url,
            organizations_url = from.organizations_url,
            repos_url = from.repos_url,
            received_events_url = from.received_events_url,
            type = from.type,
            score = from.score,
            isFavorite = from.isFavorite
        )
    }

}
