package com.snowtouch.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.snowtouch.core.presentation.components.ext.cardContentPadding

@Composable
fun ValidatedTextFieldOnCard(
    modifier: Modifier = Modifier,
    value: String,
    onNewValue: (String) -> Unit,
    label: String,
    placeholder: String,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
    var isEmpty by remember { mutableStateOf(false) }

    ElevatedCard(modifier.cardContentPadding()) {
        TextField(
            value = value,
            onValueChange = {
                onNewValue(it)
                isEmpty = it.isBlank()
            },
            modifier = modifier.fillMaxWidth(),
            label = { Text(text = label) },
            placeholder = { Text(text = placeholder) },
            isError = isEmpty,
            singleLine = singleLine,
            keyboardOptions = keyboardOptions
        )
    }
}