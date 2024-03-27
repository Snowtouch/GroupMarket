package com.snowtouch.feature_new_advertisement.di

import com.snowtouch.feature_new_advertisement.data.repository.NewAdLocalRepositoryImpl
import com.snowtouch.feature_new_advertisement.data.repository.NewAdRemoteRepositoryImpl
import com.snowtouch.feature_new_advertisement.domain.repository.NewAdLocalRepository
import com.snowtouch.feature_new_advertisement.domain.repository.NewAdRemoteRepository
import com.snowtouch.feature_new_advertisement.presentation.NewAdvertisementViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val newAdModule = module {
    singleOf(::NewAdRemoteRepositoryImpl) { bind<NewAdRemoteRepository>() }
    singleOf(::NewAdLocalRepositoryImpl) { bind<NewAdLocalRepository>() }
    single<CoroutineDispatcher> { Dispatchers.IO }

    viewModelOf(::NewAdvertisementViewModel)
}