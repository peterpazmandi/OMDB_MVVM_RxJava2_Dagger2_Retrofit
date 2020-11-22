package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass


@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)