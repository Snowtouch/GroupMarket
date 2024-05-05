package com.snowtouch.home_feature.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.core.data.SamplePreviewData
import com.snowtouch.core.di.snackbarModule
import com.snowtouch.core.navigation.NavBarItem
import com.snowtouch.core.navigation.navMenuItems
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.core.presentation.util.KoinPreviewApplication
import com.snowtouch.home_feature.di.homeModule
import com.snowtouch.home_feature.presentation.components.Home

@Composable
internal fun HomeScreen(
    viewModel : HomeViewModel,
    currentScreen : NavBarItem,
    displaySize : DisplaySize,
    navigateToAdDetails : (String) -> Unit,
    onNavMenuItemClick : (String) -> Unit,
) {
    val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    val adDetailsUiState by viewModel.adDetailsUiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.getFavoritesIds()
        viewModel.getRecentlyViewedAdvertisements()
        viewModel.getNewAdvertisements()
        viewModel.getFavoriteAdvertisements()
    }

    Home(
        displaySize = displaySize,
        currentScreen = currentScreen,
        homeUiState = homeUiState,
        adDetailsUiState = adDetailsUiState,
        onAdvertisementClick = { adId ->
            viewModel.updateRecentlyViewedList(adId)
            when (displaySize) {
                DisplaySize.Compact -> navigateToAdDetails(adId)
                DisplaySize.Medium -> navigateToAdDetails(adId)
                DisplaySize.Extended -> {
                    viewModel.updateSelectedAdId(adId)
                    viewModel.getAdvertisementDetails(adId)
                }
            }
        },
        onFavoriteButtonClick = { adId -> viewModel.toggleFavoriteAd(adId) },
        onNavMenuItemClick = onNavMenuItemClick,
    )
}

@PreviewScreenSizes
@Composable
fun SampleNavigationRailComposable() {
    KoinPreviewApplication(modules = { listOf(homeModule, snackbarModule) }) {
        Home(
            displaySize = DisplaySize.Medium,
            currentScreen = navMenuItems[0],
            homeUiState = HomeUiState(
                uiState = UiState.Success,
                newAdsList = SamplePreviewData.adPreviewList,
                favoriteAdsList = SamplePreviewData.adPreviewList.asReversed(),
                recentlyViewedList = SamplePreviewData.adPreviewList.subList(0, 3)
            ),
            adDetailsUiState = AdDetailsUiState(
                uiState = UiState.Success,
                selectedAdId = SamplePreviewData.sampleAd1Preview.uid,
                adDetails = SamplePreviewData.sampleAd1Details
            ),
            onAdvertisementClick = {},
            onFavoriteButtonClick = {},
            onNavMenuItemClick = {}
        )
    }
}