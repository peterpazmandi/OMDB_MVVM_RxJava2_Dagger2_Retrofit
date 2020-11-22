package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.di.module

import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.repository.remote.MovieEnpoints
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.utils.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

@Module
class RetrofitModule {

    @Singleton
    @Provides
    fun providesOkHttpClient() : OkHttpClient {
        val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .connectTimeout(15, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit {
        return Retrofit.Builder()
            .client(providesOkHttpClient())
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesMovieEndpoint() : MovieEnpoints {
        return providesRetrofit().create(MovieEnpoints::class.java)
    }

}