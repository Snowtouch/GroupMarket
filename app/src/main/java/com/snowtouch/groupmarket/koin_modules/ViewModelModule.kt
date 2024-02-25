package com.snowtouch.groupmarket.koin_modules

import com.snowtouch.groupmarket.AuthViewModel
import com.snowtouch.groupmarket.screens.GroupMarketViewModel
import com.snowtouch.groupmarket.screens.account.AccountScreenViewModel
import com.snowtouch.groupmarket.screens.advertisement.AdvertisementDetailScreenViewModel
import com.snowtouch.groupmarket.screens.create_account.CreateAccountScreenViewModel
import com.snowtouch.groupmarket.screens.group_ads.GroupAdsScreenViewModel
import com.snowtouch.groupmarket.screens.groups.GroupsScreenViewModel
import com.snowtouch.groupmarket.screens.home.HomeScreenViewModel
import com.snowtouch.groupmarket.screens.login.LoginScreenViewModel
import com.snowtouch.groupmarket.screens.messages.MessagesScreenViewModel
import com.snowtouch.groupmarket.screens.new_advertisement.NewAdvertisementScreenViewModel
import com.snowtouch.groupmarket.screens.new_group.CreateNewGroupScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { GroupMarketViewModel() }
    viewModel { AuthViewModel(get(), get()) }
    viewModel { HomeScreenViewModel(get()) }
    viewModel { GroupsScreenViewModel(get()) }
    viewModel { GroupAdsScreenViewModel(get()) }
    viewModel { CreateNewGroupScreenViewModel(get()) }
    viewModel { NewAdvertisementScreenViewModel(get(), get()) }
    viewModel { MessagesScreenViewModel() }
    viewModel { AdvertisementDetailScreenViewModel(get()) }

    viewModel { AccountScreenViewModel(get()) }
    viewModel { LoginScreenViewModel(get()) }
    viewModel { CreateAccountScreenViewModel(get(), get()) }
}