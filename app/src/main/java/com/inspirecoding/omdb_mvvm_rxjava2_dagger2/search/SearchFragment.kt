package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.R
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.utils.Status
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

    private val TAG = this.javaClass.simpleName

    @Inject
    lateinit var viewModelFactory: SearchViewModelFactory
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)

        setupMoviesListObserver()

        viewModel.subscribeObservableOfSearchResult("Avengers")
    }

    private fun setupMoviesListObserver() {

        viewModel.searchResult.observe(viewLifecycleOwner, Observer {  _result ->
            when(_result.status)
            {

                Status.SUCCESS -> {
                    Log.d(TAG, "${_result.data}")
                }

                Status.LOADING -> {
                    Toast.makeText(context, "LOADING", Toast.LENGTH_LONG).show()
                    Log.d(TAG, "LOADING")
                }

                Status.ERROR -> {
                    Log.d(TAG, "${_result.message}")
                }

            }
        })

    }

}