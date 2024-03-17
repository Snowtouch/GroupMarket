package com.snowtouch.feature_groups.presentation.group_ads.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.core.domain.model.Response
import com.snowtouch.core.presentation.components.AdvertisementsList
import com.snowtouch.core.presentation.components.Loading
import com.snowtouch.core.presentation.components.LoadingFailed
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.feature_groups.presentation.GroupsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GroupAds(
    groupId: String,
    displaySize : DisplaySize,
    onAdvertisementCardClick: (String) -> Unit,
    modifier : Modifier = Modifier,
    viewModel: GroupsViewModel = koinViewModel()
) {
    val advertisementsResponse by viewModel.advertisementsResponse.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getGroupAdvertisements(groupId)
    }

    when (val adsResponse = advertisementsResponse) {
        is Response.Loading -> Loading(
            modifier = modifier
        )
        is Response.Success -> AdvertisementsList(
            adsList = adsResponse.data,
            displaySize = displaySize,
            onAdvertisementCardClick = onAdvertisementCardClick,
            onFavoriteButtonClick = { /*TODO*/ },
            modifier = modifier
        )
        is Response.Failure -> LoadingFailed(
            onRefreshClick = { viewModel.getGroupAdvertisements(groupId) },
            modifier = modifier,
            errorMessage = adsResponse.e.localizedMessage
        )
    }
}