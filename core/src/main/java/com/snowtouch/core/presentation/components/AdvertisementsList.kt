package com.snowtouch.core.presentation.components

import android.util.Log
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.presentation.util.DisplaySize

@Composable
fun AdvertisementsList(
    adsList : List<AdvertisementPreview>?,
    favoritesList : List<String>,
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
                    Log.d("active ads", "$ad")
                    AdvertisementCard(
                        advertisement = ad,
                        isFavorite = favoritesList.contains(ad.uid),
                        onCardClick = { onAdvertisementCardClick(ad.uid!!) },
                        onFavoriteButtonClick = { onFavoriteButtonClick(ad.uid!!) }
                    )
                }
            }
            item { Icon(imageVector = Icons.Filled.Close, contentDescription = "") }
        }
    )
}