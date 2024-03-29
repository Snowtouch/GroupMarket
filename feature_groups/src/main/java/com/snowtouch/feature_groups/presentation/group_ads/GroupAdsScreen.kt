package com.snowtouch.feature_groups.presentation.group_ads

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.core.presentation.components.CommonTopAppBar
import com.snowtouch.core.presentation.components.ScaffoldTemplate
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.feature_groups.presentation.group_ads.components.GroupAds

@Composable
fun GroupAdsScreen(
    displaySize : DisplaySize,
    groupId: String,
    onNavigateBackClick: () -> Unit,
    navigateToAdDetailsScreen: (String) -> Unit
) {

    ScaffoldTemplate(
        topBar = {
            CommonTopAppBar(
                title = "Group ads",
                canNavigateBack = true,
                onNavigateBackClick = onNavigateBackClick
            )
        }
    ) { innerPadding ->
        GroupAds(
            groupId = groupId,
            displaySize = displaySize,
            onAdvertisementCardClick = { adId -> navigateToAdDetailsScreen(adId) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}