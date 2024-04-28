package com.snowtouch.feature_advertisement_details.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.core.presentation.components.CommonTopAppBar
import com.snowtouch.core.presentation.components.SinglePageScaffold
import com.snowtouch.feature_advertisement_details.presentation.components.AdvertisementDetail

@Composable
internal fun AdvertisementDetailScreen(
    advertisementId : String,
    navigateToChatWithSeller : (String) -> Unit,
    navigateBack : () -> Unit,
) {
    SinglePageScaffold(
        topBar = {
            CommonTopAppBar(
                canNavigateBack = true,
                onNavigateBackClick = navigateBack
            )
        }
    ) { innerPadding ->
        AdvertisementDetail(
            advertisementId = advertisementId,
            navigateToChatWithSeller = navigateToChatWithSeller,
            onReserveItemClick = { TODO() },
            modifier = Modifier.padding(innerPadding)
        )
    }
}