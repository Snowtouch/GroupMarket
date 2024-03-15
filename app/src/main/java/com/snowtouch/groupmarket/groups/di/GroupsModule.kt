package com.snowtouch.groupmarket.groups.di

import com.snowtouch.groupmarket.groups.data.repository.GroupsRepositoryImpl
import com.snowtouch.groupmarket.groups.domain.repository.GroupsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val groupsModule = module {
    single<GroupsRepository> { GroupsRepositoryImpl(get(), get(), get()) }
    single<CoroutineDispatcher> { Dispatchers.IO }
}