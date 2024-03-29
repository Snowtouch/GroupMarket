package com.snowtouch.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snowtouch.core.di.snackbarModule
import com.snowtouch.core.presentation.util.SnackbarGlobalDelegate
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@Composable
fun ScaffoldTemplate(
    modifier : Modifier = Modifier,
    topBar : @Composable () -> Unit = {},
    bottomBar : @Composable () -> Unit = {},
    content : @Composable (PaddingValues) -> Unit
) {
    val snackbarGlobalDelegate = koinInject<SnackbarGlobalDelegate>()

    Scaffold(
        modifier = modifier,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarGlobalDelegate.snackbarHostState,
                modifier = modifier
                    .systemBarsPadding()
                    .wrapContentWidth(align = Alignment.Start)
                    .widthIn(max = 550.dp)
            ) {
                Snackbar(snackbarData = it)
            }
        }
    ) {
        content(it)
    }
}

@Preview(showSystemUi = true)
@Composable
fun ScaffoldTemplate() {
    KoinApplication(
        application = {
            modules(snackbarModule)
        }
    ) {
        ScaffoldTemplate(
            topBar = {
                CommonTopAppBar(
                    title = "New advertisement",
                    canNavigateBack = true,
                    onNavigateBackClick = {})
            }
        ) {
            Column {

            }
        }
    }
}