package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.di.module

import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.detail.DetailFragment
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindSearchFragment() : SearchFragment

    @ContributesAndroidInjector
    abstract fun bindDetailFragment() : DetailFragment

}