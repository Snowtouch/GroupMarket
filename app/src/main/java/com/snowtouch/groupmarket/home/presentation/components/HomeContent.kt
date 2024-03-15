package com.snowtouch.groupmarket.home.presentation.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.snowtouch.groupmarket.core.domain.model.Advertisement

@Composable
fun HomeContent(

    newestAds: List<Advertisement>? = emptyList(),
    favoritesAds: List<Advertisement>? = emptyList(),
    recentlyWatchedAds: List<Advertisement>? = emptyList(),
    onAdvertisementClick: (String) -> Unit,
    onFavoriteButtonClick: (String) -> Unit
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