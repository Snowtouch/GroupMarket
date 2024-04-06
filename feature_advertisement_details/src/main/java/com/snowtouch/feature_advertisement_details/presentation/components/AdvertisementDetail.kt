package com.snowtouch.feature_advertisement_details.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.presentation.components.Loading
import com.snowtouch.core.presentation.components.LoadingFailed
import com.snowtouch.feature_advertisement_details.presentation.AdvertisementDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun AdvertisementDetail(
    advertisementId: String,
    navigateBack: () -> Unit,
    modifier : Modifier = Modifier,
    viewModel : AdvertisementDetailViewModel = koinViewModel(),
) {
    val adDetailData by viewModel.adDetailsResult.collectAsStateWithLifecycle()
    val userFavorites by viewModel.currentUserFavoriteAdsIds.collectAsStateWithLifecycle(
        initialValue = emptyList()
    )

    LaunchedEffect(Unit) {
        viewModel.getAdvertisementDetails(advertisementId)
    }

    when (val adDetail = adDetailData) {
        is Result.Loading -> Loading(modifier = modifier)
        is Result.Success -> AdvertisementDetailContent(
            advertisement = adDetail.data?: Advertisement(),
            isFavorite = userFavorites.contains(advertisementId),
            onFavoriteButtonClick = { viewModel.toggleFavoriteAd(advertisementId)},
            modifier = modifier,
        )
        is Result.Failure -> LoadingFailed(
            canRefresh = true,
            onErrorIconClick = { viewModel.getAdvertisementDetails(advertisementId) },
            modifier = modifier,
            errorMessage = adDetail.e.localizedMessage
        )
    }
}