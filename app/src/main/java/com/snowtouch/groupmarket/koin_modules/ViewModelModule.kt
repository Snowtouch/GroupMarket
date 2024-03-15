package com.snowtouch.groupmarket.koin_modules

import com.snowtouch.groupmarket.MainViewModel
import com.snowtouch.groupmarket.account.presentation.AccountViewModel
import com.snowtouch.groupmarket.advertisement_details.presentation.AdvertisementDetailScreenViewModel
import com.snowtouch.groupmarket.auth.presentation.create_account.CreateAccountScreenViewModel
import com.snowtouch.groupmarket.auth.presentation.login.LoginViewModel
import com.snowtouch.groupmarket.core.presentation.GroupMarketViewModel
import com.snowtouch.groupmarket.groups.presentation.GroupsViewModel
import com.snowtouch.groupmarket.groups.presentation.new_group.CreateNewGroupViewModel
import com.snowtouch.groupmarket.home.presentation.HomeViewModel
import com.snowtouch.groupmarket.messages.presentation.messages.MessagesScreenViewModel
import com.snowtouch.groupmarket.new_advertisement.presentation.NewAdvertisementScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { GroupMarketViewModel() }
    viewModel { MainViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { GroupsViewModel(get()) }
    viewModel { CreateNewGroupViewModel(get()) }
    viewModel { NewAdvertisementScreenViewModel(get(), get()) }
    viewModel { MessagesScreenViewModel() }
    viewModel { AdvertisementDetailScreenViewModel(get()) }

    viewModel { AccountViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { CreateAccountScreenViewModel(get()) }
}