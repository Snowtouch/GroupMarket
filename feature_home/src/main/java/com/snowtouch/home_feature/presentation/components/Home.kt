package com.snowtouch.home_feature.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Response
import com.snowtouch.core.presentation.components.Loading
import com.snowtouch.core.presentation.components.LoadingFailed
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.home_feature.presentation.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Home(
    displaySize : DisplaySize,
    onAdvertisementClick : (String) -> Unit,
    onFavoriteButtonClick : (String) -> Unit,
    modifier : Modifier = Modifier,
    viewModel : HomeViewModel = koinViewModel(),
) {
    val newAdsDataResponse by viewModel.newAdsDataResponse.collectAsStateWithLifecycle()
    val favoriteAdsDataResponse by viewModel.favoriteAdsDataResponse.collectAsStateWithLifecycle()
    val recentlyWatchedAdsDataResponse by viewModel.recentlyViewedAdsDataResponse.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getLatestAdvertisements()
        viewModel.getUserFavoriteAdvertisements()
        viewModel.getUserRecentlyViewedAds()
    }
    when {
        newAdsDataResponse is Response.Loading ||
                favoriteAdsDataResponse is Response.Loading ||
                recentlyWatchedAdsDataResponse is Response.Loading -> {
            Loading()
        }

        newAdsDataResponse is Response.Failure ||
                favoriteAdsDataResponse is Response.Failure ||
                recentlyWatchedAdsDataResponse is Response.Failure -> {
            LoadingFailed(
                onRefreshClick = {
                    viewModel.getLatestAdvertisements()
                    viewModel.getUserFavoriteAdvertisements()
                    viewModel.getUserRecentlyViewedAds()
                },
                errorMessage = "Error loading data"
            )
        }

        newAdsDataResponse is Response.Success &&
                favoriteAdsDataResponse is Response.Success &&
                recentlyWatchedAdsDataResponse is Response.Success -> {
            HomeContent(
                newestAds = (newAdsDataResponse as Response.Success<List<AdvertisementPreview>>).data,
                favoritesAds = (favoriteAdsDataResponse as Response.Success<List<AdvertisementPreview>>).data,
                recentlyWatchedAds = (recentlyWatchedAdsDataResponse as Response.Success<List<AdvertisementPreview>>).data,
                onAdvertisementClick = { adId -> onAdvertisementClick(adId) },
                onFavoriteButtonClick = { adId -> onFavoriteButtonClick(adId) }
            )
        }
    }
}