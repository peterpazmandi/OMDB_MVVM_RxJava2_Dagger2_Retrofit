package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.di.module

import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.repository.MovieRepository
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.repository.MovieRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class MovieRepositoryModule {

    @Binds
    abstract fun provideMovieRepository (
        movieRepositoryImpl: MovieRepositoryImpl
    ) : MovieRepository

}