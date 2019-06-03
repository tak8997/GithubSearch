package com.github.byungtak.githubsearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.github.byungtak.domain.UserRepository
import com.github.byungtak.domain.common.DomainTestUtils
import com.github.byungtak.domain.common.TestTransformer
import com.github.byungtak.domain.usecases.GetFavoriteUser
import com.github.byungtak.domain.usecases.RemoveFavoriteUser
import com.github.byungtak.githubsearch.entities.User
import com.github.byungtak.githubsearch.ui.favorite.FavoriteViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@Suppress("UNCHECKED_CAST")
@RunWith(JUnit4::class)
class FavoriteViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testUserId = 0
    private val testUserPosition = 0

    private val userEntityUserMapper = UserEntityUserMapper()

    private lateinit var userRepository: UserRepository
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteUsersObserver: Observer<List<User>>
    private lateinit var removeFavoriteUserObserver: Observer<Pair<User, Int>>

    @Before
    fun setupFavoriteViewModel() {
        MockitoAnnotations.initMocks(this)

        userRepository = mock(UserRepository::class.java)
        favoriteUsersObserver = mock(Observer::class.java) as Observer<List<User>>
        removeFavoriteUserObserver = mock(Observer::class.java) as Observer<Pair<User, Int>>

        val getFavoriteUserUseCase = GetFavoriteUser(TestTransformer(), userRepository)
        val removeFavoriteUser = RemoveFavoriteUser(TestTransformer(), userRepository)

        favoriteViewModel = FavoriteViewModel(
            getFavoriteUserUseCase,
            removeFavoriteUser,
            userEntityUserMapper
        )
        favoriteViewModel.favoriteUsers.observeForever(favoriteUsersObserver)
        favoriteViewModel.removeUser.observeForever(removeFavoriteUserObserver)
    }

    @Test
    fun fetchFavoriteUsersFromRepository() {
        val userEntities = DomainTestUtils.generateUserEntityList()

        `when`(userRepository.getFavoriteUsers()).thenReturn(Observable.just(userEntities))
        favoriteViewModel.getFavoriteUsers()

        val users = userEntities.map { userEntityUserMapper.mapFrom(it) }
        verify(favoriteUsersObserver).onChanged(users)
    }

    @Test
    fun onFavoriteButtonClicked() {
        val userEntities = DomainTestUtils.generateUserStateFavoriteEntityList()

        favoriteViewModel.userEntities.addAll(userEntities)
        val userEntity = DomainTestUtils.getTestUserEntity(testUserId)
        `when`(userRepository.removeFavoriteUser(userEntity)).thenReturn(Completable.complete())

        val user = userEntityUserMapper.mapFrom(userEntity)
        favoriteViewModel.onFavoriteButtonClicked(user, testUserPosition)
        verify(removeFavoriteUserObserver).onChanged(Pair(user, testUserPosition))
    }

}
