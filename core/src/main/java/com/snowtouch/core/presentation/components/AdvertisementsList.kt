package com.snowtouch.core.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            if (displaySize == DisplaySize.Compact) 1
            else 2
        ),
        modifier = modifier,
        state = state,
        contentPadding = PaddingValues(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            if (!adsList.isNullOrEmpty()) {
                items(adsList) { ad ->
                    Log.d("active ads", "$ad")
                    AdvertisementCard(
                        advertisement = ad,
                        isFavorite = favoritesList.contains(ad.uid),
                        onCardClick = { onAdvertisementCardClick(ad.uid!!) },
                        onFavoriteButtonClick = { onFavoriteButtonClick(ad.uid!!) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Nothing to show")
                    }
                }
            }
        }
    )
}