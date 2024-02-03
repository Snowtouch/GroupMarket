package com.snowtouch.groupmarket.koin_modules

import com.snowtouch.groupmarket.model.service.AccountService
import com.snowtouch.groupmarket.model.service.DatabaseService
import com.snowtouch.groupmarket.model.service.StorageService
import com.snowtouch.groupmarket.model.service.impl.AccountServiceImpl
import com.snowtouch.groupmarket.model.service.impl.DatabaseServiceImpl
import com.snowtouch.groupmarket.model.service.impl.StorageServiceImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val serviceModule = module {
    single<AccountService> { AccountServiceImpl(get(), get()) }
    single<DatabaseService> { DatabaseServiceImpl(get(), get(), get()) }
    single<StorageService> { StorageServiceImpl(get()) }

    single<CoroutineDispatcher> { Dispatchers.IO }
}
