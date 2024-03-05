package com.snowtouch.groupmarket.core.presentation.components.ext

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.cardContentPadding(): Modifier {
    return this.fillMaxWidth().padding(8.dp)
}

fun Modifier.textFieldPadding(): Modifier {
    return this.padding(4.dp)
}