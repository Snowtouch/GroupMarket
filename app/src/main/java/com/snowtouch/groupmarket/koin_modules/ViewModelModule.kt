package com.snowtouch.groupmarket.koin_modules

import com.snowtouch.groupmarket.MainViewModel
import com.snowtouch.groupmarket.core.presentation.GroupMarketViewModel
import com.snowtouch.groupmarket.account.presentation.AccountScreenViewModel
import com.snowtouch.groupmarket.advertisement_details.presentation.AdvertisementDetailScreenViewModel
import com.snowtouch.groupmarket.auth.presentation.create_account.CreateAccountScreenViewModel
import com.snowtouch.groupmarket.groups.presentation.group_ads.GroupAdsScreenViewModel
import com.snowtouch.groupmarket.groups.presentation.groups.GroupsScreenViewModel
import com.snowtouch.groupmarket.home.presentation.HomeScreenViewModel
import com.snowtouch.groupmarket.auth.presentation.login.LoginScreenViewModel
import com.snowtouch.groupmarket.messages.presentation.messages.MessagesScreenViewModel
import com.snowtouch.groupmarket.new_advertisement.presentation.NewAdvertisementScreenViewModel
import com.snowtouch.groupmarket.groups.presentation.new_group.CreateNewGroupScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { GroupMarketViewModel() }
    viewModel { MainViewModel(get()) }
    viewModel { HomeScreenViewModel(get()) }
    viewModel { GroupsScreenViewModel(get()) }
    viewModel { GroupAdsScreenViewModel(get()) }
    viewModel { CreateNewGroupScreenViewModel(get()) }
    viewModel { NewAdvertisementScreenViewModel(get(), get()) }
    viewModel { MessagesScreenViewModel() }
    viewModel { AdvertisementDetailScreenViewModel(get()) }

    viewModel { AccountScreenViewModel(get()) }
    viewModel { LoginScreenViewModel(get()) }
    viewModel { CreateAccountScreenViewModel(get()) }
}