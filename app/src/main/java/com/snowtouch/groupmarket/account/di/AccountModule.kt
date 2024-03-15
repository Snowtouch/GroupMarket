package com.snowtouch.groupmarket.account.di

import com.snowtouch.groupmarket.account.data.repository.AccountRepositoryImpl
import com.snowtouch.groupmarket.account.domain.repository.AccountRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val accountModule = module {
    single<AccountRepository> { AccountRepositoryImpl(get(), get(), get()) }
    single<CoroutineDispatcher> { Dispatchers.IO }
}