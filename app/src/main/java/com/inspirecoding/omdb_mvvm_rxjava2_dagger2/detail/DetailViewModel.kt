package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Movie
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Resource
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.repository.MovieRepository
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.utils.API_KEY
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailViewModel @Inject constructor (
    private val movieRepository: MovieRepository
): ViewModel() {

    val TAG = this.javaClass.simpleName

    private lateinit var movieResultObservable : Flowable<Movie>

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    private val _movieResult = MutableLiveData<Resource<Movie>>()
    val movieResult : LiveData<Resource<Movie>> = _movieResult

    fun subscribeObservableMovieResult (title : String) {
        movieResultObservable = movieRepository.getMovieDetailsData(title, API_KEY)

        val result = movieResultObservable
            .onBackpressureBuffer()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { resultMovie ->
                    _movieResult.postValue(Resource.loading())

                    _movieResult.postValue(Resource.success(resultMovie))


                }, { throwable ->

                    throwable.message?.let { _message ->
                        _movieResult.postValue(Resource.error(_message))
                    }

                }
            )

        compositeDisposable.add(result)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}