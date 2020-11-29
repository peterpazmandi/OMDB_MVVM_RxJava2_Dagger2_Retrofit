package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.di.module

import androidx.lifecycle.ViewModel
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.detail.DetailViewModel
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.di.ViewModelKey
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel (viewModel: SearchViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel::class)
    abstract fun bindDetailViewModel (viewModel: DetailViewModel) : ViewModel
}