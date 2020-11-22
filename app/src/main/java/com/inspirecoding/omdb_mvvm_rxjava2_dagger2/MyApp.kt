package com.inspirecoding.omdb_mvvm_rxjava2_dagger2

import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MyApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }
}