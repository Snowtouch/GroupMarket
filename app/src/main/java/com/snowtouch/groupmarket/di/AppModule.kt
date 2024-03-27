package com.snowtouch.groupmarket.di

import com.snowtouch.groupmarket.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainViewModel(get()) }
}