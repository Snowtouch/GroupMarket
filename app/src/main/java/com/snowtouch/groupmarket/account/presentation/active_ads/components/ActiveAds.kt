package com.snowtouch.groupmarket.account.presentation.active_ads.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.groupmarket.account.presentation.AccountViewModel
import com.snowtouch.groupmarket.core.domain.model.Response
import com.snowtouch.groupmarket.core.presentation.components.Loading
import com.snowtouch.groupmarket.core.presentation.components.theme.LoadingFailed
import com.snowtouch.groupmarket.core.presentation.util.DisplaySize
import org.koin.androidx.compose.koinViewModel

@Composable
fun ActiveAds(
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

        is Response.Success -> TODO()

        is Response.Failure -> LoadingFailed(
            onRefreshClick = { viewModel.getUserActiveAds() }
        )
    }
}