package com.snowtouch.groupmarket.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CommonButton(
    onClick: () -> Unit,
    text: String
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(16.dp, 8.dp, 16.dp, 0.dp),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) { Text(text = text) }
}

@Preview
@Composable
fun CommonButtonPreview() {
    CommonButton(onClick = {  }, text = "Text")
}