package com.snowtouch.feature_groups.di

import com.snowtouch.feature_groups.data.repository.GroupsRepositoryImpl
import com.snowtouch.feature_groups.domain.repository.GroupsRepository
import com.snowtouch.feature_groups.presentation.GroupsViewModel
import com.snowtouch.feature_groups.presentation.new_group.CreateNewGroupViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val groupsModule = module {
    single<GroupsRepository> { GroupsRepositoryImpl(get(), get(), get()) }
    single<CoroutineDispatcher> { Dispatchers.IO }

    viewModel { GroupsViewModel(get(), get(), get()) }
    viewModel { CreateNewGroupViewModel(get()) }
}