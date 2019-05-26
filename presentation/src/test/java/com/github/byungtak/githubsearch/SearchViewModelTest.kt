package com.github.byungtak.githubsearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.github.byungtak.domain.UserRepository
import com.github.byungtak.domain.common.DomainTestUtils
import com.github.byungtak.domain.common.TestTransformer
import com.github.byungtak.domain.entities.UserEntity
import com.github.byungtak.domain.usecases.*
import com.github.byungtak.githubsearch.entities.User
import com.github.byungtak.githubsearch.ui.search.SearchViewModel
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@Suppress("UNCHECKED_CAST")
@RunWith(JUnit4::class)
class SearchViewModelTest {

    private val userEntityUserMapper = UserEntityUserMapper()

    @get:Rule var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var userRepository: UserRepository
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var userEntities: List<UserEntity>
    private lateinit var favoriteUsersObserver: Observer<List<User>>

    @Before
    fun setupSearchViewModel() {
        MockitoAnnotations.initMocks(this)

        userEntities = DomainTestUtils.generateUserEntityList()

        userRepository = mock(UserRepository::class.java)
        favoriteUsersObserver = mock(Observer::class.java) as Observer<List<User>>

        val searchUserUseCase = SearchUser(TestTransformer(), userRepository)
        val getFavoriteUserUseCase = GetFavoriteUser(TestTransformer(), userRepository)
        val saveFavoriteUserUseCase = SaveFavoriteUser(TestTransformer(), userRepository)
        val removeFavoriteUser = RemoveFavoriteUser(TestTransformer(), userRepository)
        val removeAllFavoriteUser = RemoveAllFavoriteUser(TestTransformer(), userRepository)

        searchViewModel = SearchViewModel(
            searchUserUseCase,
            getFavoriteUserUseCase,
            saveFavoriteUserUseCase,
            removeFavoriteUser,
            removeAllFavoriteUser,
            userEntityUserMapper
        )
        searchViewModel.favoriteUsers.observeForever(favoriteUsersObserver)
    }


    @Test
    fun fetchUsersFromRepository() {
        Mockito.`when`(userRepository.getFavoriteUsers()).thenReturn(Observable.just(userEntities))
        searchViewModel.getFavoriteUsers()

        val users = userEntities.map { userEntityUserMapper.mapFrom(it) }
        verify(favoriteUsersObserver).onChanged(users)
    }


}