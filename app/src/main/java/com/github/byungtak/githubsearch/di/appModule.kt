package com.github.byungtak.githubsearch.di

import com.github.byungtak.githubsearch.BuildConfig
import com.github.byungtak.githubsearch.data.UserDataRepository
import com.github.byungtak.githubsearch.data.UserRepository
import com.github.byungtak.githubsearch.data.local.UserLocalDataRepository
import com.github.byungtak.githubsearch.data.local.UserLocalRepository
import com.github.byungtak.githubsearch.data.remote.ApiService
import com.github.byungtak.githubsearch.data.remote.UserRemoteDataRepository
import com.github.byungtak.githubsearch.data.remote.UserRemoteRepository
import com.github.byungtak.githubsearch.ui.MainViewModel
import com.github.byungtak.githubsearch.ui.favorite.FavoriteViewModel
import com.github.byungtak.githubsearch.ui.search.SearchViewModel
import com.github.byungtak.githubsearch.util.SchedulersProvider
import com.github.byungtak.githubsearch.util.SchedulersProviderImpl
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val LOGGING_INTERCEPTOR = "LOGGING_INTERCEPTOR"
private const val HEADER_AUTH_KEY = "Authorization"
private const val HEADER_AUTH_VALUE = "32f7a97b0f2a8c69d04fed5f8ec4917db5a4a9d5"

val appModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }

    single { SchedulersProviderImpl() as SchedulersProvider }

    single { UserDataRepository(get(), get(), get()) as UserRepository }
    single { UserLocalDataRepository() as UserLocalRepository }
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