package com.github.byungtak.domain

import com.github.byungtak.domain.common.DomainTestUtils
import com.github.byungtak.domain.common.TestTransformer
import com.github.byungtak.domain.usecases.GetFavoriteUser
import com.github.byungtak.domain.usecases.SearchUser
import io.reactivex.Observable
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class UseCaseTests {

    private val testQuery = "tak8997"
    private val testPage = 0

    @Test
    fun searchUsers() {
        val userRepository = Mockito.mock(UserRepository::class.java)
        val searchUser = SearchUser(TestTransformer(), userRepository)
        `when`(userRepository.searchUser(testQuery, testPage)).thenReturn(Observable.just(DomainTestUtils.generateUserEntityList()))

        searchUser
            .searchUsers(testQuery, testPage)
            .test()
            .assertValue { results ->
                results.size == 5
            }
            .assertComplete()
    }

    @Test
    fun getFavoriteUser() {
        val userRepository = Mockito.mock(UserRepository::class.java)
        val getFavoriteUser = GetFavoriteUser(TestTransformer(), userRepository)
        `when`(userRepository.getFavoriteUsers()).thenReturn(Observable.just(DomainTestUtils.generateUserEntityList()))

        getFavoriteUser
            .observable()
            .test()
            .assertValue { results ->
                results.size == 5
            }
            .assertComplete()
    }
}