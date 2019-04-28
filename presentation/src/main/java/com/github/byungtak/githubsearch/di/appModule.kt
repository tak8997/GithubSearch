package com.github.byungtak.githubsearch.di

import com.github.byungtak.data.api.ApiService
import com.github.byungtak.data.repositories.UserDataRepository
import com.github.byungtak.data.repositories.UserLocalDataRepository
import com.github.byungtak.data.repositories.UserRemoteDataRepository
import com.github.byungtak.domain.UserLocalRepository
import com.github.byungtak.domain.UserRemoteRepository
import com.github.byungtak.domain.UserRepository
import com.github.byungtak.domain.common.Mapper
import com.github.byungtak.domain.entities.UserEntity
import com.github.byungtak.domain.usecases.GetFavoriteUser
import com.github.byungtak.domain.usecases.RemoveFavoriteUser
import com.github.byungtak.domain.usecases.SaveFavoriteUser
import com.github.byungtak.domain.usecases.SearchUser
import com.github.byungtak.githubsearch.BuildConfig
import com.github.byungtak.githubsearch.UserEntityUserMapper
import com.github.byungtak.githubsearch.common.ASyncTransformer
import com.github.byungtak.githubsearch.data.model.User
import com.github.byungtak.githubsearch.ui.favorite.FavoriteViewModel
import com.github.byungtak.githubsearch.ui.search.SearchViewModel
import com.github.byungtak.githubsearch.util.SchedulersProvider
import com.github.byungtak.githubsearch.util.SchedulersProviderImpl
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val LOGGING_INTERCEPTOR = "LOGGING_INTERCEPTOR"
private const val HEADER_AUTH_KEY = "Authorization"
private const val HEADER_AUTH_VALUE = "32f7a97b0f2a8c69d04fed5f8ec4917db5a4a9d5"

val appModule = module {
    viewModel { SearchViewModel(get(), get(), get(), get(), get()) }
    viewModel { FavoriteViewModel(get()) }

    single { UserEntityUserMapper() as Mapper<UserEntity, User> }
    single { SchedulersProviderImpl() as SchedulersProvider }

    // search viewmodel
    factory { SearchUser(ASyncTransformer(), get()) }
    factory { GetFavoriteUser(ASyncTransformer(), get()) }
    factory { SaveFavoriteUser(ASyncTransformer(), get()) }
    factory { RemoveFavoriteUser(ASyncTransformer(), get()) }

    single { UserDataRepository(get(), get()) as UserRepository }
    single { UserLocalDataRepository(androidApplication()) as UserLocalRepository }
    single { UserRemoteDataRepository(get()) as UserRemoteRepository }

    single { Gson() }
    single(LOGGING_INTERCEPTOR) {
        val logger = HttpLoggingInterceptor()

        logger.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        logger as Interceptor
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(get(LOGGING_INTERCEPTOR))
            .addInterceptor {
                val original = it.request()

                val requestBuilder = original.newBuilder()
                    .header(HEADER_AUTH_KEY, HEADER_AUTH_VALUE)

                val request = requestBuilder.build()
                it.proceed(request)
            }
            .build()
    }
    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}