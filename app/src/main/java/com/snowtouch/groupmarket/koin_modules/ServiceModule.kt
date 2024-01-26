package com.snowtouch.groupmarket.koin_modules

import com.snowtouch.groupmarket.model.service.impl.AccountServiceImpl
import com.snowtouch.groupmarket.model.service.impl.DatabaseServiceImpl
import com.snowtouch.groupmarket.model.service.impl.StorageServiceImpl
import org.koin.dsl.module

val serviceModule = module {
    single { AccountServiceImpl(get()) }
    single { DatabaseServiceImpl(get(), get()) }
    single { StorageServiceImpl() }
}