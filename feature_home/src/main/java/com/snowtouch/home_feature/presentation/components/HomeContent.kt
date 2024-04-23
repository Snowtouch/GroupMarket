package com.snowtouch.home_feature.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.core.domain.model.AdvertisementPreview

@Composable
internal fun HomeContent(
    favoriteAdIds : List<String>,
    newestAds : List<AdvertisementPreview>,
    favoriteAdsList : List<AdvertisementPreview>,
    recentlyViewedAds : List<AdvertisementPreview>,
    onAdvertisementClick : (String) -> Unit,
    onFavoriteButtonClick : (String) -> Unit,
    modifier : Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        AdHorizontalList(
            gridTitle = "New advertisements",
            modifier = Modifier,
            advertisementList = newestAds,
            favoriteIdsList = favoriteAdIds,
            onAdvertisementClick = onAdvertisementClick,
            onFavoriteButtonClick = onFavoriteButtonClick
        )

        AdHorizontalList(
            gridTitle = "Your favorites",
            modifier = Modifier,
            advertisementList = favoriteAdsList,
            favoriteIdsList = favoriteAdIds,
            onAdvertisementClick = onAdvertisementClick,
            onFavoriteButtonClick = onFavoriteButtonClick
        )

        AdHorizontalList(
            gridTitle = "Recently watched",
            modifier = Modifier,
            advertisementList = recentlyViewedAds,
            favoriteIdsList = favoriteAdIds,
            onAdvertisementClick = onAdvertisementClick,
            onFavoriteButtonClick = onFavoriteButtonClick
        )

    }
}