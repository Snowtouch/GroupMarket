package com.snowtouch.groupmarket.core.presentation.util

import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.snowtouch.groupmarket.core.presentation.components.theme.md_theme_light_error
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class SnackbarState {
    DEFAULT, ERROR
}

class SnackbarGlobalDelegate(
    var snackbarHostState: SnackbarHostState,
    private var coroutineScope: CoroutineScope
) {
    private var snackbarState: SnackbarState = SnackbarState.DEFAULT
    val snackbarBackgroundColor: Color

    @Composable
    get() = when (snackbarState) {
        SnackbarState.DEFAULT -> SnackbarDefaults.color
        SnackbarState.ERROR -> md_theme_light_error
    }

    fun showSnackbar(
        state: SnackbarState,
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean,
        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
        this.snackbarState = state
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message, actionLabel, withDismissAction, duration)
        }
    }
}