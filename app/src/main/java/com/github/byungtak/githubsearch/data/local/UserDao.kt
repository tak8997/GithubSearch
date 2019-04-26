/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.byungtak.githubsearch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.byungtak.githubsearch.data.model.User
import io.reactivex.Completable

import io.reactivex.Flowable

/**
 * Data Access Object for the users table.
 */
@Dao
internal interface UserDao {

    @Query("SELECT * FROM Users WHERE isfavorite = 1")
    fun getFavoriteUsers(): Flowable<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteUser(user: User): Completable

    @Query("DELETE FROM users WHERE userid= :userId")
    fun deleteFavoriteUser(userId: String): Completable

    @Query("DELETE FROM Users")
    fun deleteAllUsers()

}