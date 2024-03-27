package com.snowtouch.feature_new_advertisement.presentation.advertisement_post_result.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.snowtouch.core.presentation.components.CommonButton
import com.snowtouch.core.presentation.components.ext.adaptiveColumnWidth
import com.snowtouch.core.presentation.components.theme.GroupMarketTheme

@Composable
fun AdvertisementPostResultContent(
    onGoToHomeScreenClick : () -> Unit,
    onGoToPostedAdDetailsClick : () -> Unit,
    modifier : Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .adaptiveColumnWidth()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.TwoTone.ThumbUp,
            contentDescription = null,
            modifier = Modifier.scale(2f)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Your advertisement has been added successfully",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        CommonButton(
            onClick = onGoToHomeScreenClick,
            text = "Go to Home Screen"
        )
        CommonButton(
            onClick = onGoToPostedAdDetailsClick,
            text = "Go to posted advertisement"
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun AdvertisementPostResultContentPreview() {
    GroupMarketTheme {
        AdvertisementPostResultContent( {}, {} )
    }
}