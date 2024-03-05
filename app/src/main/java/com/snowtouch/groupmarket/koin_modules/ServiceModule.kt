package com.snowtouch.groupmarket.koin_modules

import com.snowtouch.groupmarket.auth.domain.repository.AuthRepository
import com.snowtouch.groupmarket.core.domain.repository.DatabaseRepository
import com.snowtouch.groupmarket.core.domain.repository.StorageRepository
import com.snowtouch.groupmarket.auth.data.repository.AuthRepositoryImpl
import com.snowtouch.groupmarket.model.service.impl.DatabaseRepositoryImpl
import com.snowtouch.groupmarket.model.service.impl.StorageRepositoryImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val serviceModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<DatabaseRepository> { DatabaseRepositoryImpl(get(), get(), get()) }
    single<StorageRepository> { StorageRepositoryImpl(get(), get()) }

    single<CoroutineDispatcher> { Dispatchers.IO }
}
