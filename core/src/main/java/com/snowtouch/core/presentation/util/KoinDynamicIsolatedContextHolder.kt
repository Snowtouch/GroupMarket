package com.snowtouch.core.presentation.util

import org.koin.core.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.koinApplication

//first Koin instance for preview
class DynamicIsolatedContextHolder(
    modules: List<Module>
) {

    val koinApp: KoinApplication = koinApplication(
        appDeclaration = { modules(modules) }
    )
}