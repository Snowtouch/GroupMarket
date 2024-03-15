package com.snowtouch.groupmarket.core.presentation.components

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.groupmarket.core.domain.model.AdvertisementPreview
import com.snowtouch.groupmarket.core.presentation.util.DisplaySize

@Composable
fun AdvertisementsList(
    adsList : List<AdvertisementPreview>?,
    displaySize : DisplaySize,
    onAdvertisementCardClick : (String) -> Unit,
    onFavoriteButtonClick : (String) -> Unit,
    modifier : Modifier = Modifier,
) {
    val state = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(
            if (displaySize == DisplaySize.Compact) 2
            else 3
        ),
        modifier = modifier,
        state = state,
        content = {
            if (!adsList.isNullOrEmpty()) {
                items(adsList) { ad ->
                    AdvertisementCard(
                        advertisement = ad,
                        onCardClick = { onAdvertisementCardClick(ad.uid!!) },
                        onFavoriteButtonClick = { onFavoriteButtonClick(ad.uid!!) }
                    )
                }
            }
        }
    )
}