package com.snowtouch.feature_new_advertisement.di

import com.snowtouch.feature_new_advertisement.data.repository.NewAdLocalRepositoryImpl
import com.snowtouch.feature_new_advertisement.data.repository.NewAdRemoteRepositoryImpl
import com.snowtouch.feature_new_advertisement.domain.repository.NewAdLocalRepository
import com.snowtouch.feature_new_advertisement.domain.repository.NewAdRemoteRepository
import com.snowtouch.feature_new_advertisement.presentation.NewAdvertisementViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


val newAdModule: Module = module {
    single<CoroutineDispatcher> { Dispatchers.IO }
    single<NewAdRemoteRepository> { NewAdRemoteRepositoryImpl(get(), get(), get(), get()) }
    single<NewAdLocalRepository> { NewAdLocalRepositoryImpl() }
    viewModel { NewAdvertisementViewModel(get(), get()) }}
