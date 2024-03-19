package com.snowtouch.core.di

import androidx.compose.material3.SnackbarHostState
import com.snowtouch.core.presentation.util.SnackbarGlobalDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val snackbarModule = module {
    single { SnackbarHostState() }
    single { CoroutineScope(SupervisorJob() + Dispatchers.Main) }
    single { SnackbarGlobalDelegate(get(), get()) }
}