package com.github.byungtak.githubsearch.di

import com.github.byungtak.githubsearch.BuildConfig
import com.github.byungtak.githubsearch.data.SearchDataRepository
import com.github.byungtak.githubsearch.data.SearchRepository
import com.github.byungtak.githubsearch.data.remote.ApiService
import com.github.byungtak.githubsearch.ui.MainViewModel
import com.github.byungtak.githubsearch.ui.favorite.FavoriteViewModel
import com.github.byungtak.githubsearch.ui.search.SearchViewModel
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

val appModule = module {
    viewModel { MainViewModel() }
    viewModel { SearchViewModel(get()) }
    viewModel { FavoriteViewModel() }

    single { SearchDataRepository() as SearchRepository }

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