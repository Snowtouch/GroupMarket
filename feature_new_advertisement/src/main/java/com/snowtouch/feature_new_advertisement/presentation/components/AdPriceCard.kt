package com.snowtouch.feature_new_advertisement.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun AdPriceCard(
    modifier: Modifier = Modifier,
    price: String,
    onAdPriceChanged: (String) -> Unit
) {
    ValidatedTextFieldOnCard(
        modifier = modifier,
        value = price,
        onNewValue = onAdPriceChanged,
        label = "Price",
        placeholder = "Enter item price",
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        )
    )
}