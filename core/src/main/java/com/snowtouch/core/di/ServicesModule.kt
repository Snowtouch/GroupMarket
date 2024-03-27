package com.snowtouch.core.di

import com.snowtouch.core.data.repository.DatabaseReferenceManagerImpl
import com.snowtouch.core.domain.repository.DatabaseReferenceManager
import org.koin.dsl.module

val servicesModule = module {
    single<DatabaseReferenceManager> { DatabaseReferenceManagerImpl(get(), get()) }
}