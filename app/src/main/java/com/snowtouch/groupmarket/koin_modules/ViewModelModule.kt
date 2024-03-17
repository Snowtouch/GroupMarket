package com.snowtouch.groupmarket.koin_modules

import com.snowtouch.core.presentation.GroupMarketViewModel
import com.snowtouch.groupmarket.MainViewModel
import com.snowtouch.groupmarket.messages.presentation.messages.MessagesScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { GroupMarketViewModel() }
    viewModel { MainViewModel(get()) }
    viewModel { com.snowtouch.feature_groups.presentation.GroupsViewModel(get()) }
    viewModel { com.snowtouch.feature_groups.presentation.new_group.CreateNewGroupViewModel(get()) }
    viewModel {
        com.snowtouch.feature_new_advertisement.presentation.NewAdvertisementViewModel(
            get(),
            get()
        )
    }
    viewModel { MessagesScreenViewModel() }
    viewModel {
        com.snowtouch.feature_advertisement_details.presentation.AdvertisementDetailViewModel(
            get()
        )
    }

    viewModel { com.snowtouch.account_feature.presentation.AccountViewModel(get()) }
    viewModel { com.snowtouch.auth_feature.presentation.login.LoginViewModel(get()) }
    viewModel { com.snowtouch.auth_feature.presentation.create_account.CreateAccountViewModel(get()) }
}