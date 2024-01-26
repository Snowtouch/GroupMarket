package com.snowtouch.groupmarket.koin_modules

import androidx.lifecycle.viewmodel.compose.viewModel
import com.snowtouch.groupmarket.screens.GroupMarketViewModel
import com.snowtouch.groupmarket.screens.home.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { HomeScreenViewModel(get()) }
    viewModel { GroupMarketViewModel() }
}