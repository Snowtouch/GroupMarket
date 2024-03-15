package com.snowtouch.groupmarket.core.presentation.components.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingFailed(
    onRefreshClick: () -> Unit,
    modifier : Modifier = Modifier,
    errorMessage: String? = "Error"
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = onRefreshClick){
                Icon(
                    imageVector = Icons.Rounded.Refresh,
                    contentDescription = "Tap to refresh",
                    modifier = Modifier.size(52.dp),
                    tint = Color.Gray.copy(alpha = 0.8f)
                )
            }
            Text(
                text = "$errorMessage.\r\nTap HERE to try again",
                color = Color.Gray.copy(alpha = 0.9f),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoadingFailedPreview() {
    LoadingFailed({})
}