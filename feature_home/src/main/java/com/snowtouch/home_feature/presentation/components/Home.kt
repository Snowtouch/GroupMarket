package com.snowtouch.home_feature.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.snowtouch.core.data.SamplePreviewData
import com.snowtouch.core.di.snackbarModule
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.navigation.NavBarItem
import com.snowtouch.core.navigation.navMenuItems
import com.snowtouch.core.presentation.components.AdaptiveNavigationBar
import com.snowtouch.core.presentation.components.Loading
import com.snowtouch.core.presentation.components.LoadingFailed
import com.snowtouch.core.presentation.components.SinglePageScaffold
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.core.presentation.util.KoinPreviewApplication
import com.snowtouch.home_feature.di.homeModule
import com.snowtouch.home_feature.presentation.AdDetailsUiState
import com.snowtouch.home_feature.presentation.HomeUiState
import com.snowtouch.home_feature.presentation.UiState

@Composable
internal fun Home(
    displaySize : DisplaySize,
    currentScreen : NavBarItem,
    homeUiState : HomeUiState,
    adDetailsUiState : AdDetailsUiState,
    navigateToAdvertisementDetails : (String) -> Unit,
    onFavoriteButtonClick : (String) -> Unit,
    onNavMenuItemClick : (String) -> Unit,
) {
    SinglePageScaffold(
        bottomBar = {
            AdaptiveNavigationBar(
                currentScreen = currentScreen,
                displaySize = displaySize,
                onNavMenuItemClick = onNavMenuItemClick
            )
        },
        modifier = Modifier,
    ) {
        Row(
            modifier = Modifier
                .padding(start = if (displaySize != DisplaySize.Compact) 82.dp else 0.dp)
        ) {
            Box(modifier = Modifier.weight(0.6f)) {
                when (homeUiState.uiState) {
                    is UiState.Error -> LoadingFailed(canRefresh = false)
                    is UiState.Loading -> Loading()
                    is UiState.Success -> {
                        HomeContent(
                            favoriteAdIds = homeUiState.favoritesIdsList,
                            onAdvertisementClick = navigateToAdvertisementDetails,
                            onFavoriteButtonClick = onFavoriteButtonClick,
                            modifier = Modifier,
                            newestAds = homeUiState.newAdsList,
                            favoriteAdsList = homeUiState.favoriteAdsList,
                            recentlyViewedAds = homeUiState.recentlyViewedList
                        )
                    }
                }
            }
            if (displaySize == DisplaySize.Extended) {
                VerticalDivider()
                Box(
                    modifier = Modifier.weight(0.4f),
                    contentAlignment = Alignment.TopCenter
                ) {
                    ExtendedScreenDetailsContent(
                        adDetailsUiState = adDetailsUiState.uiState,
                        selectedAdId = adDetailsUiState.selectedAdId,
                        selectedAdDetails = adDetailsUiState.adDetails ?: Advertisement(),
                        favoriteAdsIdsList = homeUiState.favoritesIdsList,
                        onFavoriteButtonClick = onFavoriteButtonClick,
                        onContactSellerClick = {},
                        onReserveItemClick = {}
                    )
                }
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun HomeScreenPreview() {
    KoinPreviewApplication(
        modules = { listOf(homeModule, snackbarModule) }
    ) {
        Home(
            displaySize = DisplaySize.Compact,
            currentScreen = navMenuItems[0],
            homeUiState = HomeUiState(
                uiState = UiState.Success,
                newAdsList = SamplePreviewData.adPreviewList,
                favoriteAdsList = SamplePreviewData.adPreviewList.asReversed(),
                recentlyViewedList = SamplePreviewData.adPreviewList.subList(0, 3)),
            adDetailsUiState = AdDetailsUiState(
                uiState = UiState.Success,
                selectedAdId = SamplePreviewData.sampleAd1Preview.uid,
                adDetails = SamplePreviewData.sampleAd1Details),
            navigateToAdvertisementDetails = {},
            onFavoriteButtonClick = {},
            onNavMenuItemClick = {},
        )
    }
}