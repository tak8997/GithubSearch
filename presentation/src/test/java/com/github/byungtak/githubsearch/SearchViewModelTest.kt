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
import io.reactivex.Completable
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
    private lateinit var favoriteStateObserver: Observer<Boolean>

    private val testUserId = 100

    @Before
    fun setupSearchViewModel() {
        MockitoAnnotations.initMocks(this)

        userEntities = DomainTestUtils.generateUserEntityList()

        userRepository = mock(UserRepository::class.java)
        favoriteUsersObserver = mock(Observer::class.java) as Observer<List<User>>
        favoriteStateObserver = mock(Observer::class.java) as Observer<Boolean>

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
        searchViewModel.favoriteUsers.observeForever(favoriteUsersObserver) // favoriteUsers livedata를 영원히 감지하고있는다.
        searchViewModel.favoriteState.observeForever(favoriteStateObserver)
    }

    @Test
    fun fetchUsersFromRepository() {
        Mockito.`when`(userRepository.getFavoriteUsers()).thenReturn(Observable.just(userEntities))
        searchViewModel.getFavoriteUsers()  // _favoriteUsers에 이벤트를 줌으로써 favoriteUsers가 이벤트를 받는다.
                                            // 위의 우리가 정의한 userEntities가 변형되어 List<User> 데이터 를 받음.

        val users = userEntities.map { userEntityUserMapper.mapFrom(it) }   //그래서 변형 시켜서 List<User>를 뺸다음
        verify(favoriteUsersObserver).onChanged(users)  //변경 되었는지 확인
    }

    @Test
    fun onFavoriteButtonClicked() {
        val userEntity = DomainTestUtils.getTestUserEntity(testUserId)

        Mockito.`when`(userRepository.saveFavoriteUser(userEntity)).thenReturn(Completable.complete())
        Mockito.`when`(userRepository.removeFavoriteUser(userEntity)).thenReturn(Completable.complete())

        val user = userEntityUserMapper.mapFrom(userEntity)

        searchViewModel.onFavoriteButtonClicked(user, 0)
        verify(favoriteStateObserver).onChanged(true)

        searchViewModel.onFavoriteButtonClicked(user, 0)
        verify(favoriteStateObserver).onChanged(false)
    }

}