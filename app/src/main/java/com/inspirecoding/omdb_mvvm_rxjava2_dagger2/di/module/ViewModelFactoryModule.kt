package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.di.module

import androidx.lifecycle.ViewModelProvider
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.viewmodelprovider.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun provideViewModelFactory (viewModelProviderFactory: ViewModelFactory) : ViewModelProvider.Factory

}