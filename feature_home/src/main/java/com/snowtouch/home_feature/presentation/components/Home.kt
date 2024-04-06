package com.snowtouch.home_feature.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.core.presentation.components.Loading
import com.snowtouch.core.presentation.components.LoadingFailed
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.home_feature.presentation.HomeUiState
import com.snowtouch.home_feature.presentation.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun Home(
    displaySize : DisplaySize,
    onAdvertisementClick : (String) -> Unit,
    onFavoriteButtonClick : (String) -> Unit,
    modifier : Modifier = Modifier,
    viewModel : HomeViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is HomeUiState.Loading -> { Loading(modifier = modifier) }
        is HomeUiState.Error -> {
            LoadingFailed(
                canRefresh = true,
                onErrorIconClick = {
                    viewModel.getLatestAdvertisements()
                    viewModel.getUserFavoriteAdvertisements()
                    viewModel.getUserRecentlyViewedAds() },
                modifier = modifier,
                errorMessage = (uiState as HomeUiState.Error).e.localizedMessage
            )
        }
        is HomeUiState.Success -> {
            HomeContent(
                newestAds = (uiState as HomeUiState.Success).newAdsList,
                favoritesAds = (uiState as HomeUiState.Success).userFavoritesList,
                recentlyWatchedAds = (uiState as HomeUiState.Success).recentlyWatchedAdsList,
                onAdvertisementClick = { adId -> onAdvertisementClick(adId) },
                onFavoriteButtonClick = { adId -> onFavoriteButtonClick(adId) }
            )
        }
    }
}