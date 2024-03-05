package com.snowtouch.groupmarket.auth.di

import com.snowtouch.groupmarket.auth.data.repository.AuthRepositoryImpl
import com.snowtouch.groupmarket.auth.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    single<CoroutineDispatcher> { Dispatchers.IO }
}