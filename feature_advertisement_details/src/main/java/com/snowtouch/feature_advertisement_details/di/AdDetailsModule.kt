package com.snowtouch.feature_advertisement_details.di

import com.snowtouch.feature_advertisement_details.data.repository.AdDetailsRepositoryImpl
import com.snowtouch.feature_advertisement_details.domain.repository.AdDetailsRepository
import com.snowtouch.feature_advertisement_details.presentation.AdvertisementDetailViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val adDetailsModule = module {
    single<AdDetailsRepository> { AdDetailsRepositoryImpl(get()) }
    single<CoroutineDispatcher> { Dispatchers.IO }

    viewModel { AdvertisementDetailViewModel(get(), get(), get()) }
}