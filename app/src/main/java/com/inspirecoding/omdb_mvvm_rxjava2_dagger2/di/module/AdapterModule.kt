package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.di.module

import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model.Search
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.search.adapter.SearchResultAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class AdapterModule {

    @Provides
    fun providesSearchArrayList() = ArrayList<Search>()

    @Provides
    fun providesSearchResultAdapter(searchItems : ArrayList<Search>) = SearchResultAdapter()

}