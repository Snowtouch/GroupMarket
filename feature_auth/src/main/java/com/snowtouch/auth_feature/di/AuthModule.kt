package com.snowtouch.auth_feature.di

import com.snowtouch.auth_feature.data.repository.AuthRepositoryImpl
import com.snowtouch.auth_feature.domain.repository.AuthRepository
import com.snowtouch.auth_feature.presentation.create_account.CreateAccountViewModel
import com.snowtouch.auth_feature.presentation.login.LoginViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }
    single<CoroutineDispatcher> { Dispatchers.IO }

    viewModel { LoginViewModel(get()) }
    viewModel { CreateAccountViewModel(get()) }
}