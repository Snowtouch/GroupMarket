package com.snowtouch.groupmarket.koin_modules

import com.snowtouch.groupmarket.model.service.impl.AccountServiceImpl
import com.snowtouch.groupmarket.model.service.impl.DatabaseServiceImpl
import com.snowtouch.groupmarket.model.service.impl.StorageServiceImpl
import org.koin.dsl.module

val serviceModule = module {
    single { AccountServiceImpl(get(), get()) }
    single { DatabaseServiceImpl(get(), get(), get()) }
    single { StorageServiceImpl(get()) }
}