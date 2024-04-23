package com.snowtouch.feature_groups.presentation.group_ads

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.core.presentation.components.CommonTopAppBar
import com.snowtouch.core.presentation.components.SinglePageScaffold
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.feature_groups.presentation.GroupsViewModel
import com.snowtouch.feature_groups.presentation.group_ads.components.GroupAds

@Composable
internal fun GroupAdsScreen(
    viewModel : GroupsViewModel,
    groupId : String,
    displaySize : DisplaySize,
    onNavigateBackClick : () -> Unit,
    navigateToAdDetailsScreen : (String) -> Unit,
) {
    val groupAdsUiState by viewModel.groupAdsUiState.collectAsStateWithLifecycle()
    viewModel.getGroupAdvertisements(groupId)

    SinglePageScaffold(
        topBar = {
            CommonTopAppBar(
                title = "Group ads",
                canNavigateBack = true,
                onNavigateBackClick = onNavigateBackClick
            )
        }
    ) { innerPadding ->
        GroupAds(
            displaySize = displaySize,
            groupAdsUiState = groupAdsUiState,
            onAdvertisementCardClick = { adId -> navigateToAdDetailsScreen(adId) },
            onFavoriteButtonClick = { adId -> viewModel.toggleFavoriteAd(adId) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}