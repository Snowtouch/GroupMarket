package com.snowtouch.groupmarket.groups.presentation.group_ads.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.groupmarket.core.domain.model.Response
import com.snowtouch.groupmarket.core.presentation.components.AdvertisementsList
import com.snowtouch.groupmarket.core.presentation.components.Loading
import com.snowtouch.groupmarket.core.presentation.components.theme.LoadingFailed
import com.snowtouch.groupmarket.core.presentation.util.DisplaySize
import com.snowtouch.groupmarket.groups.presentation.GroupsViewModel
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