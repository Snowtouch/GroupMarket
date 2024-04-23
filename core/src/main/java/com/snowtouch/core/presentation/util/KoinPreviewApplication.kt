package com.snowtouch.core.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.koin.compose.KoinIsolatedContext
import org.koin.core.context.GlobalContext
import org.koin.core.module.Module

//Support for multiple previews when using Koin
@Composable
fun KoinPreviewApplication(
    modules: () -> List<Module>,
    content: @Composable () -> Unit
) {
    var isInitialComposition: Boolean by remember { mutableStateOf(true) }
    var koinApplicationHolder: DynamicIsolatedContextHolder = remember { DynamicIsolatedContextHolder(modules()) }

    KoinIsolatedContext(
        context = koinApplicationHolder.koinApp,
        content = {
            DisposableEffect(modules()) {
                if (!isInitialComposition) {
                    GlobalContext.stopKoin()
                    koinApplicationHolder = DynamicIsolatedContextHolder(modules())
                }
                isInitialComposition = false
                onDispose {
                    GlobalContext.stopKoin()
                }
            }
            content()
        }
    )
}