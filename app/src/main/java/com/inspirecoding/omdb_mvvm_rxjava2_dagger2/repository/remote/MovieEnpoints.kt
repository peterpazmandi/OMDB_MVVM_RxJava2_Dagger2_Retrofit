package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.repository.remote

import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Movie
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.SearchResults
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieEnpoints {

    @GET("?type=movie")
    fun getSearchResultData(
        @Query(value = "s") searchTitle : String,
        @Query(value = "apiKey") apiKey : String,
        @Query(value = "page") pageIndex : Int
    ) : Flowable<SearchResults>

    @GET("?plot=full")
    fun getMovieDetailsData(
        @Query(value = "t") title : String,
        @Query(value = "apiKey") apiKey : String
    ) : Flowable<Movie>

}