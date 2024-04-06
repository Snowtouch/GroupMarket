package com.snowtouch.core.presentation.components.ext

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.cardContentPadding(): Modifier {
    return this.fillMaxWidth().padding(8.dp)
}

fun Modifier.textFieldPadding(): Modifier {
    return this.padding(8.dp)
}

fun Modifier.adaptiveColumnWidth(): Modifier {
    return this.sizeIn(minWidth = 200.dp, maxWidth = 600.dp)
}