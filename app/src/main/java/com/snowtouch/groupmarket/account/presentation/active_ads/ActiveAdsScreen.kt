package com.snowtouch.groupmarket.account.presentation.active_ads

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.groupmarket.account.presentation.active_ads.components.ActiveAds
import com.snowtouch.groupmarket.core.presentation.components.CommonTopAppBar
import com.snowtouch.groupmarket.core.presentation.components.ScaffoldTemplate
import com.snowtouch.groupmarket.core.presentation.util.DisplaySize

@Composable
fun ActiveAdsScreen(
    displaySize : DisplaySize,
    navigateBack : () -> Unit,
    navigateToAdDetails : (String) -> Unit,
) {
    ScaffoldTemplate(
        topBar = {
            CommonTopAppBar(
                title = "Your active ads",
                canNavigateBack = true,
                onNavigateBackClick = navigateBack
            )
        }
    ) { innerPadding ->
        ActiveAds(
            displaySize = displaySize,
            onAdvertisementCardClick = { adId -> navigateToAdDetails(adId) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}