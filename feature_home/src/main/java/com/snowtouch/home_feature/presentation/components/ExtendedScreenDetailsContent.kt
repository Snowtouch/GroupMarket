package com.snowtouch.home_feature.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.presentation.components.Loading
import com.snowtouch.core.presentation.components.LoadingFailed
import com.snowtouch.feature_advertisement_details.presentation.components.AdvertisementDetailContent
import com.snowtouch.home_feature.presentation.UiState

@Composable
internal fun ExtendedScreenDetailsContent(
    adDetailsUiState : UiState,
    selectedAdId : String?,
    selectedAdDetails : Advertisement,
    favoriteAdsIdsList : List<String>,
    onFavoriteButtonClick : (String) -> Unit,
    onContactSellerClick : (String) -> Unit,
    onReserveItemClick : (String) -> Unit
) {
    AnimatedVisibility(
        visible = selectedAdId != null,
        modifier = Modifier
    ) {
        when (adDetailsUiState) {
            is UiState.Loading -> Loading()
            is UiState.Error -> LoadingFailed(canRefresh = false)
            is UiState.Success -> {
                AdvertisementDetailContent(
                    advertisement = selectedAdDetails,
                    isFavorite = favoriteAdsIdsList.contains(selectedAdId),
                    onFavoriteButtonClick = onFavoriteButtonClick,
                    onContactSellerClick = onContactSellerClick,
                    onReserveItemClick = onReserveItemClick,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }
        }
    }
    AnimatedVisibility(visible = selectedAdId == null) {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Select advertisement", color = Color.Gray)
        }
    }
}