package com.snowtouch.feature_groups.presentation.group_ads.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.core.presentation.components.AdvertisementsList
import com.snowtouch.core.presentation.components.Loading
import com.snowtouch.core.presentation.components.LoadingFailed
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.feature_groups.presentation.GroupsViewModel
import com.snowtouch.feature_groups.presentation.group_ads.GroupAdsUiState
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun GroupAds(
    groupId: String,
    displaySize : DisplaySize,
    onAdvertisementCardClick: (String) -> Unit,
    modifier : Modifier = Modifier,
    viewModel: GroupsViewModel = koinViewModel()
) {
    val uiState by viewModel.groupAdsUiState.collectAsStateWithLifecycle()
    val userFavorites by viewModel.currentUserFavoriteAdsIds.collectAsStateWithLifecycle(
        initialValue = emptyList()
    )

    LaunchedEffect(Unit) {
        viewModel.getGroupAdvertisements(groupId)
    }

    when (uiState) {
        is GroupAdsUiState.Loading -> Loading(
            modifier = modifier
        )
        is GroupAdsUiState.Success -> AdvertisementsList(
            adsList = (uiState as GroupAdsUiState.Success).groupAds,
            favoritesList = userFavorites,
            displaySize = displaySize,
            onAdvertisementCardClick = onAdvertisementCardClick,
            onFavoriteButtonClick = { adId -> viewModel.toggleFavoriteAd(adId) },
            modifier = modifier
        )
        is GroupAdsUiState.Error -> LoadingFailed(
            canRefresh = true,
            onErrorIconClick = { viewModel.getGroupAdvertisements(groupId) },
            modifier = modifier,
            errorMessage = (uiState as GroupAdsUiState.Error).e.localizedMessage
        )
    }
}