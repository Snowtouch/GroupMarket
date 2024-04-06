package com.snowtouch.core.di

import com.snowtouch.core.data.repository.CoreRepositoryImpl
import com.snowtouch.core.domain.repository.CoreRepository
import org.koin.dsl.module

val commonRepositoryModule = module {
    single<CoreRepository> { CoreRepositoryImpl(get(), get()) }
}