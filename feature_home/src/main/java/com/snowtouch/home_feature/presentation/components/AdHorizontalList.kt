package com.snowtouch.home_feature.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.presentation.components.AdvertisementCard
import com.snowtouch.core.presentation.components.theme.GroupMarketTheme

@Composable
internal fun AdHorizontalList(
    gridTitle : String,
    modifier : Modifier = Modifier,
    advertisementList : List<AdvertisementPreview>,
    favoriteIdsList : List<String>,
    onAdvertisementClick : (String) -> Unit,
    onFavoriteButtonClick : (String) -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            text = gridTitle,
            modifier = Modifier.padding(start = 8.dp),
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall,
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement =
            if (advertisementList.isEmpty()) {
                Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            } else {
                Arrangement.spacedBy(8.dp)
            },
            content = {
                when {
                    advertisementList.isEmpty() -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Nothing to show")
                            }
                        }
                    }

                    else -> {
                        items(advertisementList) { advertisement ->
                            AdvertisementCard(
                                advertisement = advertisement,
                                isFavorite = favoriteIdsList.contains(advertisement.uid),
                                onCardClick = { onAdvertisementClick(advertisement.uid!!) },
                                onFavoriteButtonClick = onFavoriteButtonClick
                            )
                        }
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(showSystemUi = true)
@Composable
fun HorizontalListPreview() {
    GroupMarketTheme {
        AdHorizontalList(
            gridTitle = "New advertisements",
            advertisementList = emptyList(),
            favoriteIdsList = listOf("232"),
            onAdvertisementClick = {},
            onFavoriteButtonClick = {}
        )
    }
}