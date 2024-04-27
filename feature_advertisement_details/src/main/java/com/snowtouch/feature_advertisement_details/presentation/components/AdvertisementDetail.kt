package com.snowtouch.feature_advertisement_details.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.core.presentation.components.Loading
import com.snowtouch.core.presentation.components.LoadingFailed
import com.snowtouch.feature_advertisement_details.presentation.AdvertisementDetailViewModel
import com.snowtouch.feature_advertisement_details.presentation.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun AdvertisementDetail(
    advertisementId : String,
    navigateToChatWithSeller : (String) -> Unit,
    onReserveItemClick : (String) -> Unit,
    modifier : Modifier = Modifier,
    viewModel : AdvertisementDetailViewModel = koinViewModel(),
) {
    val adDetailsUiState by viewModel.adDetailsUiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAdvertisementDetails(advertisementId)
    }

    when (adDetailsUiState.uiState) {
        is UiState.Loading -> Loading(modifier = modifier)
        is UiState.Success -> AdvertisementDetailContent(
            advertisement = adDetailsUiState.advertisement,
            isFavorite = adDetailsUiState.favoritesIdsList.contains(advertisementId),
            onFavoriteButtonClick = { viewModel.toggleFavoriteAd(advertisementId) },
            onContactSellerClick = navigateToChatWithSeller,
            onReserveItemClick = onReserveItemClick,
            modifier = modifier,
        )

        is UiState.Error -> LoadingFailed(
            canRefresh = true,
            onErrorIconClick = { viewModel.getAdvertisementDetails(advertisementId) },
            modifier = modifier,
            errorMessage = (adDetailsUiState.uiState as UiState.Error).e.localizedMessage
        )
    }
}