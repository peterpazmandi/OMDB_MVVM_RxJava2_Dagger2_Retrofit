package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.repository

import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Movie
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.SearchResults
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.repository.remote.MovieEnpoints
import io.reactivex.Flowable
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor (
    private val movieEndpoints: MovieEnpoints
) : MovieRepository {

    override fun getSearchResultData(
        searchTitle: String,
        apiKey: String,
        pageIndex: Int
    ): Flowable<SearchResults> = movieEndpoints.getSearchResultData(
        searchTitle, apiKey, pageIndex
    )

    override fun getMovieDetailsData(
        title: String,
        apiKey: String): Flowable<Movie> = movieEndpoints.getMovieDetailsData(
        title, apiKey
    )

}