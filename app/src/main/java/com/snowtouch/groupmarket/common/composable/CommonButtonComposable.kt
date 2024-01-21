package com.snowtouch.groupmarket.common.composable

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.snowtouch.groupmarket.common.ext.textButton

@Composable
fun CommonButton(
    onClick: () -> Unit,
    text: String
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.textButton(),
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