package com.snowtouch.home_feature.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.snowtouch.core.domain.model.AdvertisementPreview

@Composable
fun HomeContent(

    newestAds : List<AdvertisementPreview>? = emptyList(),
    favoritesAds : List<AdvertisementPreview>? = emptyList(),
    recentlyWatchedAds : List<AdvertisementPreview>? = emptyList(),
    onAdvertisementClick : (String) -> Unit,
    onFavoriteButtonClick : (String) -> Unit
) {
    LazyColumn {
        item {
            AdHorizontalList(
                gridTitle = "Newest advertisements",
                advertisementList = newestAds,
                onAdvertisementClick = onAdvertisementClick,
                onFavoriteButtonClick = onFavoriteButtonClick
            )
        }
        item {
            AdHorizontalList(
                gridTitle = "Your favorites",
                advertisementList = favoritesAds,
                onAdvertisementClick = onAdvertisementClick,
                onFavoriteButtonClick = onFavoriteButtonClick
            )
        }
        item {
            AdHorizontalList(
                gridTitle = "Recently watched",
                advertisementList = recentlyWatchedAds,
                onAdvertisementClick = onAdvertisementClick,
                onFavoriteButtonClick = onFavoriteButtonClick
            )
        }
    }
}