package com.snowtouch.account_feature.presentation.active_ads.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.account_feature.presentation.AccountViewModel
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.presentation.components.AdvertisementsList
import com.snowtouch.core.presentation.components.Loading
import com.snowtouch.core.presentation.components.LoadingFailed
import com.snowtouch.core.presentation.util.DisplaySize
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ActiveAds(
    displaySize : DisplaySize,
    onAdvertisementCardClick : (String) -> Unit,
    modifier : Modifier = Modifier,
    viewModel : AccountViewModel = koinViewModel(),
) {
    val activeAdsResponse by viewModel.activeAdsResult.collectAsStateWithLifecycle()
    val userFavorites by viewModel.currentUserFavoriteAdsIds.collectAsStateWithLifecycle(
        initialValue = emptyList()
    )

    LaunchedEffect(Unit) {
        viewModel.getUserActiveAds()
    }

    when (val activeAdsData = activeAdsResponse) {
        is Result.Loading -> Loading(modifier = modifier)

        is Result.Success -> AdvertisementsList(
            adsList = activeAdsData.data,
            favoritesList = userFavorites,
            displaySize = displaySize,
            onAdvertisementCardClick = onAdvertisementCardClick,
            onFavoriteButtonClick = { adId -> viewModel.toggleFavoriteAd(adId)},
            modifier = modifier
        )

        is Result.Failure -> LoadingFailed(
            canRefresh = true,
            onErrorIconClick = { viewModel.getUserActiveAds() },
            modifier = modifier,
            errorMessage = activeAdsData.e.localizedMessage
        )
    }
}