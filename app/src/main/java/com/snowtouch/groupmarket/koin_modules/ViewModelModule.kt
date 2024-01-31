package com.snowtouch.groupmarket.koin_modules

import androidx.lifecycle.viewmodel.compose.viewModel
import com.snowtouch.groupmarket.screens.GroupMarketViewModel
import com.snowtouch.groupmarket.screens.account.AccountScreenViewModel
import com.snowtouch.groupmarket.screens.advertisement.AdvertisementScreenViewModel
import com.snowtouch.groupmarket.screens.groups.GroupsScreenViewModel
import com.snowtouch.groupmarket.screens.home.HomeScreenViewModel
import com.snowtouch.groupmarket.screens.messages.MessagesScreenViewModel
import com.snowtouch.groupmarket.screens.new_advertisement.NewAdvertisementScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { GroupMarketViewModel() }
    viewModel { HomeScreenViewModel(get()) }
    viewModel { GroupsScreenViewModel() }
    viewModel { NewAdvertisementScreenViewModel(get()) }
    viewModel { MessagesScreenViewModel() }
    viewModel { AdvertisementScreenViewModel(get()) }
    viewModel { AccountScreenViewModel() }
}