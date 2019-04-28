package com.github.byungtak.data.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val BASE_URL = "https://api.github.com"
    }

    @GET("/search/users")
    fun searchUser(@Query("q") query: String,
                   @Query("page") page: Int,
                   @Query("per_page") perPgae: Int = 15): Observable<UserModel>

}