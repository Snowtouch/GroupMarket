package com.snowtouch.feature_messages.di

import com.snowtouch.feature_messages.presentation.messages.MessagesViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val messagesModule = module {
    viewModel { MessagesViewModel() }
    single<CoroutineDispatcher> { Dispatchers.IO }
}