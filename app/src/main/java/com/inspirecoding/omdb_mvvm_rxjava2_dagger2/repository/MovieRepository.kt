package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.repository

import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Movie
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.SearchResults
import io.reactivex.Flowable

interface MovieRepository {

    fun getSearchResultData(
        searchTitle : String,
        apiKey : String,
        pageIndex : Int
    ) : Flowable<SearchResults>

    fun getMovieDetailsData(
        title : String,
        apiKey : String
    ) : Flowable<Movie>

}