package com.snowtouch.account_feature.di

import com.snowtouch.account_feature.data.repository.AccountRepositoryImpl
import com.snowtouch.account_feature.domain.repository.AccountRepository
import com.snowtouch.account_feature.presentation.AccountViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val accountModule = module {
    single<AccountRepository> { AccountRepositoryImpl(get(), get(), get()) }
    single<CoroutineDispatcher> { Dispatchers.IO }

    viewModel { AccountViewModel(get()) }
}