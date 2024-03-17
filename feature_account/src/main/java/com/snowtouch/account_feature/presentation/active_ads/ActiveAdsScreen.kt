package com.snowtouch.account_feature.presentation.active_ads

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.account_feature.presentation.active_ads.components.ActiveAds
import com.snowtouch.core.presentation.components.CommonTopAppBar
import com.snowtouch.core.presentation.components.ScaffoldTemplate
import com.snowtouch.core.presentation.util.DisplaySize

@Composable
internal fun ActiveAdsScreen(
    displaySize : DisplaySize,
    navigateBack : () -> Unit,
    onAdCardClick : (String) -> Unit,
) {
    ScaffoldTemplate(
        topBar = {
            CommonTopAppBar(
                title = "Your active ads",
                canNavigateBack = displaySize == DisplaySize.Compact,
                onNavigateBackClick = navigateBack
            )
        }
    ) { innerPadding ->
        ActiveAds(
            displaySize = displaySize,
            onAdvertisementCardClick = { adId -> onAdCardClick(adId) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}