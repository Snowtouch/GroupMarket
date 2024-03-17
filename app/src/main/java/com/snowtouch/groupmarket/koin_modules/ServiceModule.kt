package com.snowtouch.groupmarket.koin_modules

import com.snowtouch.core.domain.repository.DatabaseRepository
import com.snowtouch.core.domain.repository.StorageRepository
import com.snowtouch.groupmarket.model.service.impl.DatabaseRepositoryImpl
import com.snowtouch.groupmarket.model.service.impl.StorageRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val serviceModule = module {
    single<com.snowtouch.core.domain.repository.DatabaseRepository> { DatabaseRepositoryImpl(get(), get(), get()) }
    single<com.snowtouch.core.domain.repository.StorageRepository> { StorageRepositoryImpl(get(), get()) }

    single<CoroutineDispatcher> { Dispatchers.IO }
}
