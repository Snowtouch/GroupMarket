package com.snowtouch.account_feature.presentation.active_ads.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.account_feature.presentation.AccountViewModel
import com.snowtouch.core.domain.model.Response
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
    val activeAdsResponse by viewModel.activeAdsResponse.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getUserActiveAds()
    }

    when (val activeAdsData = activeAdsResponse) {
        is Response.Loading -> Loading(modifier = modifier)

        is Response.Success -> AdvertisementsList(
            adsList = activeAdsData.data,
            displaySize = displaySize,
            onAdvertisementCardClick = onAdvertisementCardClick,
            onFavoriteButtonClick = { TODO() },
            modifier = modifier
        )

        is Response.Failure -> LoadingFailed(
            onRefreshClick = { viewModel.getUserActiveAds() },
            modifier = modifier,
            errorMessage = activeAdsData.e.localizedMessage
        )
    }
}