package com.snowtouch.feature_new_advertisement.presentation.advertisement_post_result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.snowtouch.core.presentation.components.CommonButton

@Composable
fun AdvertisementPostSuccess(
    navigateToHome : () -> Unit,
    navigateToAdDetails : () -> Unit,
    modifier : Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Filled.ThumbUp,
                contentDescription = "Success",
                modifier = Modifier.scale(2f)
            )
            CommonButton(onClick = navigateToAdDetails, text = "Go to posted advertisement")
            CommonButton(onClick = navigateToHome, text = "Go to Home screen")
        }
    }
}