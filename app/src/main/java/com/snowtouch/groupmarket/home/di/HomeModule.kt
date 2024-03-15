package com.snowtouch.groupmarket.home.di

import com.snowtouch.groupmarket.home.data.repository.HomeRepositoryImpl
import com.snowtouch.groupmarket.home.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val homeModule = module {
    single<HomeRepository> { HomeRepositoryImpl(get(), get(), get()) }
    single<CoroutineDispatcher> { Dispatchers.IO }
}