package com.snowtouch.feature_new_advertisement.presentation.new_advertisement.components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snowtouch.core.presentation.components.ext.textFieldPadding

@Composable
fun TitleTextField(
    title : String,
    onNewValueTitle : (String) -> Unit,
    isError : Boolean,
) {
    TextField(
        value = title,
        onValueChange = onNewValueTitle,
        modifier = Modifier
            .fillMaxWidth()
            .textFieldPadding(),
        label = { Text(text = "Title") },
        placeholder = { Text(text = "Enter item title") },
        supportingText = { Text(text = "Characters: ${title.length}/100")},
        isError = isError
    )
}

@Composable
fun DescriptionTextField(
    description : String,
    onDescriptionChanged : (String) -> Unit,
    isError : Boolean,
) {
    TextField(
        value = description,
        onValueChange = onDescriptionChanged,
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 200.dp)
            .textFieldPadding(),
        label = { Text(text = "Description") },
        placeholder = { Text(text = "Enter item description") },
        supportingText = { Text(text = "Characters: ${description.length}/500") },
        isError = isError,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default)
    )
}

@Composable
fun PriceTextField(
    price : String,
    onPriceChanged : (String) -> Unit,
    isError : Boolean
) {
    TextField(
        value = price,
        onValueChange = onPriceChanged,
        modifier = Modifier
            .fillMaxWidth()
            .textFieldPadding(),
        label = { Text(text = "Price") },
        placeholder = { Text(text = "Enter item price") },
        isError = isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        )
    )
}

@Preview
@Composable
private fun TitleFieldPreview() {
    TitleTextField(
        title = "",
        onNewValueTitle = {},
        isError = false
    )
}