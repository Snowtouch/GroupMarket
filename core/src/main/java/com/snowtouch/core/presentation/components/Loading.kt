package com.snowtouch.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Loading(
    modifier : Modifier = Modifier,
    progress : Float? = null,
    message : String? = null,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        if (progress != null)
            CircularProgressIndicator(
                progress = { progress },
            )
        else
            CircularProgressIndicator()
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = message ?: "")
    }
}