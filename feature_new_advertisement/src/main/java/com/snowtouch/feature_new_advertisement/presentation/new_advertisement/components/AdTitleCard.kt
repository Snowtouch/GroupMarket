package com.snowtouch.feature_new_advertisement.presentation.new_advertisement.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.core.presentation.components.ext.cardContentPadding

@Composable
fun AdTitleCard(
    modifier: Modifier = Modifier,
    title: String,
    onNewValueTitle: (String) -> Unit
) {
    ElevatedCard(modifier.cardContentPadding()) {
        TextField(
            value = title,
            onValueChange = onNewValueTitle,
            modifier = modifier.fillMaxWidth(),
            label = { Text(text = "Title") },
            placeholder = { Text(text = "Enter item title") }
        )
    }
}