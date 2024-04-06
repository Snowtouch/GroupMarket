package com.snowtouch.core.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.core.presentation.components.ext.cardContentPadding

@Composable
fun ValidatedTextFieldOnCard(
    modifier : Modifier = Modifier,
    value : String,
    onNewValue : (String) -> Unit,
    label : String,
    placeholder : String,
    supportingText : @Composable (() -> Unit)? = null,
    singleLine : Boolean = false,
    keyboardOptions : KeyboardOptions = KeyboardOptions.Default,
) {

    ElevatedCard(modifier.cardContentPadding()) {
        TextField(
            value = value,
            onValueChange = { onNewValue(it) },
            modifier = modifier.fillMaxWidth(),
            label = { Text(text = label) },
            placeholder = { Text(text = placeholder) },
            supportingText = supportingText,
            isError = value.isBlank(),
            singleLine = singleLine,
            keyboardOptions = keyboardOptions
        )
    }
}