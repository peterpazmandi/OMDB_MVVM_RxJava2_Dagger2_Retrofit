package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.R
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.databinding.SearchFragmentBinding
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.search.adapter.SearchResultAdapter
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.utils.Status
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.utils.gone
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.utils.visible
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.viewmodelprovider.ViewModelFactory
import dagger.android.support.DaggerFragment
import kotlinx.coroutines.delay
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

    private val TAG = this.javaClass.simpleName

    private lateinit var binding : SearchFragmentBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: SearchViewModel

    @Inject
    lateinit var searchAdapter : SearchResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = SearchFragmentBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)

        initSearchResultAdapter()
        setupMoviesListObserver()

        binding.etSearchField.addTextChangedListener {
            searchItemWithDelay(it.toString())
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.subscribeObservableOfSearchResult(binding.etSearchField.text.toString())
        }
    }

    private fun setupMoviesListObserver() {

        viewModel.searchResult.observe(viewLifecycleOwner, Observer {  _result ->
            when(_result.status)
            {
                Status.SUCCESS -> {
                    _result.data?.let {
                        searchAdapter.updateItems(it)

                        if (it.size > 0) isMovieFound(true) else isMovieFound(false)
                    }
                    binding.swipeRefreshLayout.isRefreshing = false
                }

                Status.LOADING -> {
                    binding.swipeRefreshLayout.isRefreshing = true
                }

                Status.ERROR -> {
                    Log.d(TAG, "${_result.message}")
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }
        })
    }

    private fun searchItemWithDelay(searchText : String) {

        lifecycleScope.launchWhenCreated {
            delay(5_00)

            if (searchText.isNotEmpty() && searchText.length > 3) {
                viewModel.subscribeObservableOfSearchResult(searchText)
                binding.etSearchLayout.error = null

                isMovieFound(true)
            } else {
                searchAdapter.updateItems(arrayListOf())
                binding.etSearchLayout.error = getString(R.string.type_at_least_four_characters)

                isMovieFound(false)
            }
        }

    }

    private fun isMovieFound(found : Boolean) {

        if (found) {

            binding.swipeRefreshLayout.visible()
            binding.llNoMovieFound.gone()

        } else {

            binding.swipeRefreshLayout.gone()
            binding.llNoMovieFound.visible()

        }

    }

    private fun initSearchResultAdapter() {

        binding.rvSearchResult.apply {
            this.adapter = searchAdapter
        }

    }

}