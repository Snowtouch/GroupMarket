package com.snowtouch.groupmarket.common.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Loading(
    modifier: Modifier = Modifier,
    progress: Float? = null
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        if (progress!=null)
            CircularProgressIndicator(progress = progress)
        else
            CircularProgressIndicator()
    }
}