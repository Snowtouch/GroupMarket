package com.snowtouch.groupmarket.groups.presentation.group_ads

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.groupmarket.core.presentation.components.AdvertisementCard
import com.snowtouch.groupmarket.core.domain.model.Advertisement

@Composable
fun GroupAdsScreen(
    viewModel: GroupAdsScreenViewModel,
    groupId: String,
    navigateToAdDetailsScreen: (String) -> Unit
) {
    val groupAdsFlow by viewModel.advertisementsFlow.collectAsStateWithLifecycle()

    GroupAdsScreenContent(
        groupId = groupId,
        groupAdList = groupAdsFlow,
        onAdCardClick = navigateToAdDetailsScreen)
}
@Composable
fun GroupAdsScreenContent(
    groupId: String = "",
    groupAdList: List<Advertisement> = emptyList(),
    onAdCardClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            items(groupAdList) {ad ->
                AdvertisementCard(
                    advertisement = ad,
                    onCardClick = { onAdCardClick(ad.uid!!) },
                    onFavoriteButtonClick = {}
                )
            }
        }
    )
}