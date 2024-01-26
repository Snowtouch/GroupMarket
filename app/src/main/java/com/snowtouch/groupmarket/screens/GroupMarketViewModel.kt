package com.snowtouch.groupmarket.screens

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import com.snowtouch.groupmarket.common.snackbar.SnackbarGlobalDelegate
import com.snowtouch.groupmarket.common.snackbar.SnackbarState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.java.KoinJavaComponent.get

open class GroupMarketViewModel : ViewModel(), KoinComponent {
    private val snackbarGlobalDelegate : SnackbarGlobalDelegate by inject()

    fun showSnackbar(
        state: SnackbarState,
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = true
    ) {
        snackbarGlobalDelegate.showSnackbar(state, message, actionLabel, withDismissAction)
    }
}