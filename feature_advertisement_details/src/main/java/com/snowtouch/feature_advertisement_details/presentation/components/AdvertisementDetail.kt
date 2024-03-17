package com.snowtouch.feature_advertisement_details.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Response
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
    val adDetailData by viewModel.adDetailsResponse.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getAdvertisementDetails(advertisementId)
    }

    when (val adDetail = adDetailData) {
        is Response.Loading -> Loading(modifier = modifier)
        is Response.Success -> AdvertisementDetailContent(
            advertisement = adDetail.data?: Advertisement(),
            modifier = modifier,
            onNavigateBack = navigateBack
        )
        is Response.Failure -> LoadingFailed(
            onRefreshClick = { viewModel.getAdvertisementDetails(advertisementId) },
            modifier = modifier,
            errorMessage = adDetail.e.localizedMessage
        )
    }
}