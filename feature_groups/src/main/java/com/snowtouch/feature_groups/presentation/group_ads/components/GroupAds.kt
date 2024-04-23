package com.snowtouch.feature_groups.presentation.group_ads.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.core.presentation.components.AdvertisementsList
import com.snowtouch.core.presentation.components.Loading
import com.snowtouch.core.presentation.components.LoadingFailed
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.feature_groups.presentation.groups.GroupAdsUiState
import com.snowtouch.feature_groups.presentation.groups.UiState

@Composable
internal fun GroupAds(
    displaySize : DisplaySize,
    groupAdsUiState : GroupAdsUiState,
    onAdvertisementCardClick : (String) -> Unit,
    onFavoriteButtonClick : (String) -> Unit,
    modifier : Modifier = Modifier,
) {

    when (groupAdsUiState.uiState) {
        is UiState.Loading -> Loading(
            modifier = modifier
        )

        is UiState.Success -> AdvertisementsList(
            adsList = groupAdsUiState.groupAds,
            favoritesList = groupAdsUiState.favoritesList,
            displaySize = displaySize,
            onAdvertisementCardClick = onAdvertisementCardClick,
            onFavoriteButtonClick = onFavoriteButtonClick,
            modifier = modifier
        )

        is UiState.Error -> LoadingFailed(
            canRefresh = false,
            modifier = modifier,
            errorMessage = groupAdsUiState.uiState.e.localizedMessage
        )
    }
}