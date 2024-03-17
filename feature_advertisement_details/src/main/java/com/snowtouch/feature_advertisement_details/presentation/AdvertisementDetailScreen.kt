@file:OptIn(ExperimentalFoundationApi::class)

package com.snowtouch.feature_advertisement_details.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.core.presentation.components.CommonTopAppBar
import com.snowtouch.core.presentation.components.ScaffoldTemplate
import com.snowtouch.feature_advertisement_details.presentation.components.AdvertisementDetail

@Composable
internal fun AdvertisementDetailScreen(
    advertisementId : String,
    navigateBack : () -> Unit,
) {
    ScaffoldTemplate(
        topBar = {
            CommonTopAppBar(
                canNavigateBack = true,
                onNavigateBackClick = navigateBack
            )
        }
    ) { innerPadding ->
        AdvertisementDetail(
            advertisementId = advertisementId,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

/*@Preview
@Composable
fun AdDetailScreenContentPreview() {
    AdvertisementDetailContent(
        advertisement = com.snowtouch.core.domain.model.Advertisement(
            uid = "12",
            ownerUid = "432",
            groupId = "334",
            title = "Shoes",
            description = "Description Description Description Description Description Description",
            price = 333.0,
            postDate = Date()
        ), onNavigateBack = {})
}*/