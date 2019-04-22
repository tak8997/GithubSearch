package com.github.byungtak.githubsearch.data.remote

import com.github.byungtak.githubsearch.data.model.UserModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ApiService {
    companion object {
        const val BASE_URL = "https://api.github.com"
    }

    @GET("/search/users")
    fun searchUser(@Query("q") query: String): Single<UserModel>

}