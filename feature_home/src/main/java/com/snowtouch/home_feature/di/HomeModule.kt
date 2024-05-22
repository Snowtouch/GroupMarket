package com.snowtouch.home_feature.di

import com.snowtouch.home_feature.data.repository.HomeRepositoryImpl
import com.snowtouch.home_feature.domain.repository.HomeRepository
import com.snowtouch.home_feature.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single<HomeRepository> { HomeRepositoryImpl(get()) }

    viewModel { HomeViewModel(get(), get(), get(), get(), get(), get()) }
}