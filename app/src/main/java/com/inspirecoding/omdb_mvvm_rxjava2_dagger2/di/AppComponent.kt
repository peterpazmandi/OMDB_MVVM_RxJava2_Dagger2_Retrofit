package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.di

import android.app.Application
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.MyApp
import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.di.module.*
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
        FragmentBuilderModule::class,
        ViewModelFactoryModule::class,
        ViewModelsModule::class,
        AdapterModule::class
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