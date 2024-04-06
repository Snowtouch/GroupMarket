package com.snowtouch.home_feature.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.presentation.components.AdvertisementCard

@Composable
fun AdHorizontalList(
    modifier: Modifier = Modifier,
    gridTitle: String,
    advertisementList: List<AdvertisementPreview>? = emptyList(),
    onAdvertisementClick: (String) -> Unit,
    onFavoriteButtonClick: (String) -> Unit
) {
    Column(modifier.padding(10.dp)) {
        Text(
            text = gridTitle,
            modifier.padding(bottom = 5.dp),
            style = MaterialTheme.typography.headlineSmall
        )
        LazyRow(
            content = {
                if (!advertisementList.isNullOrEmpty()) {
                    items(advertisementList) { advertisement ->
                        AdvertisementCard(
                            advertisement = advertisement,
                            onCardClick = { onAdvertisementClick(advertisement.uid!!) },
                            onFavoriteButtonClick = onFavoriteButtonClick
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                } else {
                    item { Spacer(modifier = Modifier.height(200.dp)) }

                }
            }
        )
    }
}