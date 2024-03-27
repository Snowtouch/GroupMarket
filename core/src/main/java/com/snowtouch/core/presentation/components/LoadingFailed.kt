package com.snowtouch.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingFailed(
    canRefresh : Boolean,
    onErrorIconClick: () -> Unit = {},
    modifier : Modifier = Modifier,
    errorMessage: String? = "Error"
) {

        Column(
            modifier = modifier
                .padding(horizontal = 24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (canRefresh) {
                IconButton(onClick = onErrorIconClick) {
                    Icon(
                        imageVector = Icons.Rounded.Refresh,
                        contentDescription = "Tap to refresh",
                        modifier = Modifier.scale(2f),
                        tint = Color.Gray.copy(alpha = 0.8f)
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Error",
                    modifier = Modifier.scale(2f),
                    tint = Color.Gray.copy(alpha = 0.8f)
                    )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "$errorMessage.\r\nTap HERE to try again",
                color = Color.Gray.copy(alpha = 0.9f),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center)
        }

}

@Preview(showSystemUi = true)
@Composable
fun LoadingFailedPreview() {
    LoadingFailed(false)
}