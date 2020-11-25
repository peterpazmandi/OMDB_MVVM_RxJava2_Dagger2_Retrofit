package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Resource
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Search
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.SearchResults
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.repository.MovieRepository
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.utils.API_KEY
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

class SearchViewModel @Inject constructor (
    private val movieRepository: MovieRepository
): ViewModel() {

    val TAG = this.javaClass.simpleName

    private var moviesList : ArrayList<Search> = ArrayList()
    private lateinit var searchResultObservable : Flowable<SearchResults>
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    private val _searchResult = MutableLiveData<Resource<ArrayList<Search>>>()
    val searchResult : LiveData<Resource<ArrayList<Search>>> = _searchResult

    fun subscribeObservableOfSearchResult(searchText : String) {
        searchResultObservable = movieRepository.getSearchResultData(searchText, API_KEY, 1)

        moviesList.clear()

        _searchResult.postValue(Resource.success(arrayListOf<Search>()))

        val result = searchResultObservable
            .onBackpressureBuffer()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                Flowable.fromIterable(it.Search)
            }
            .subscribeWith(createSearchResultObserver())

        compositeDisposable.add(result)
    }

    private fun createSearchResultObserver() : DisposableSubscriber<Search> {

        return object : DisposableSubscriber<Search>() {
            override fun onNext(search: Search?) {
                _searchResult.postValue(Resource.loading())

                search?.let { _search ->
                    if(!moviesList.contains(search)) moviesList.add(_search)
                }
            }

            override fun onComplete() {
                moviesList.sortByDescending {
                    it.Year
                }
                _searchResult.postValue(Resource.success(moviesList))
            }

            override fun onError(throwable: Throwable?) {
                throwable?.message?.let {  errorMessage ->
                    _searchResult.postValue(Resource.error(errorMessage))
                }
            }

        }

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}