package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.di

import android.app.Application
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.di.module.ActivityBuilderModule
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.di.module.FragmentBuilderModule
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.di.module.MovieRepositoryModule
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.di.module.RetrofitModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component (
    modules = [
        AndroidInjectionModule::class,
        RetrofitModule::class,
        MovieRepositoryModule::class,
        ActivityBuilderModule::class,
        FragmentBuilderModule::class
    ]
)

interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application (application: Application) : Builder
        fun build () : AppComponent
    }

}