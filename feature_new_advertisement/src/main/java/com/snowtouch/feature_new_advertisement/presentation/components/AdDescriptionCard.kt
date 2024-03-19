package com.snowtouch.feature_new_advertisement.presentation.components

import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun AdDescriptionCard(
    modifier: Modifier = Modifier,
    description: String,
    onAdDescriptionChanged: (String) -> Unit
) {
    ValidatedTextFieldOnCard(
        modifier = modifier.sizeIn(minHeight = 200.dp),
        value = description,
        onNewValue = onAdDescriptionChanged,
        label = "Description",
        placeholder = "Enter item description",
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default)
    )
}