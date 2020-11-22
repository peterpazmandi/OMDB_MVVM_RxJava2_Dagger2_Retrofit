package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.di.module

import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity() : MainActivity

}