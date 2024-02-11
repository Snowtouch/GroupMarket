package com.snowtouch.groupmarket.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowtouch.groupmarket.common.snackbar.SnackbarGlobalDelegate
import com.snowtouch.groupmarket.common.snackbar.SnackbarState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class GroupMarketViewModel() : ViewModel(), KoinComponent {

    private val snackbarGlobalDelegate : SnackbarGlobalDelegate by inject()

    fun showSnackbar(
        state: SnackbarState,
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = true
    ) {
        snackbarGlobalDelegate.showSnackbar(state, message, actionLabel, withDismissAction)
    }
    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar)
                    showSnackbar(SnackbarState.ERROR, throwable.message?: "Something went wrong")
            }, block = block
        )
    }
}