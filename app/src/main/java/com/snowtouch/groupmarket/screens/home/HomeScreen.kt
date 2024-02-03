package com.snowtouch.groupmarket.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.groupmarket.R
import com.snowtouch.groupmarket.common.composable.AdvertisementCard
import com.snowtouch.groupmarket.model.Advertisement
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = koinViewModel(),
    navigateToAdDetails: () -> Unit
){
    val userData by viewModel.userData.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState

    HomeScreenContent(
        newestAds = uiState.newestAds,
        favoritesAds = uiState.userFavoriteAds,
        recentlyWatchedAds = uiState.userRecentlyWatchedAds,
        onAdvertisementClick = navigateToAdDetails,
        onFavoriteButtonClick = { adId -> viewModel.favoriteAdToggle(adId) }
    )
}

@Composable
fun HomeScreenContent(
    newestAds: List<Advertisement>,
    favoritesAds: List<Advertisement>,
    recentlyWatchedAds: List<Advertisement>,
    onAdvertisementClick: () -> Unit,
    onFavoriteButtonClick: (String) -> Unit
) {
    LazyColumn {
        item {
            AdvertisementGridRow(
                gridTitle = "Newest advertisements",
                advertisementList = newestAds,
                onAdvertisementClick = onAdvertisementClick,
                onFavoriteButtonClick = onFavoriteButtonClick
            )
        }
        item {
            AdvertisementGridRow(
                gridTitle = "Your favorites",
                advertisementList = favoritesAds,
                onAdvertisementClick = onAdvertisementClick,
                onFavoriteButtonClick = onFavoriteButtonClick
            )
        }
        item {
            AdvertisementGridRow(
                gridTitle = "Recently watched",
                advertisementList = recentlyWatchedAds,
                onAdvertisementClick = onAdvertisementClick,
                onFavoriteButtonClick = onFavoriteButtonClick
            )
        }
    }
}
@Composable
fun AdvertisementGridRow(
    modifier: Modifier = Modifier,
    gridTitle: String,
    advertisementList: List<Advertisement>,
    onAdvertisementClick: () -> Unit,
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
                items(advertisementList) { advertisement ->
                    AdvertisementCard(
                        advertisement = advertisement,
                        onCardClick = onAdvertisementClick,
                        onFavoriteButtonClick = onFavoriteButtonClick
                    )
                    Spacer(modifier = Modifier.padding(end = 5.dp))
                }
            }
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    val list: List<String> = listOf(R.drawable.sample_ad_image.toString())
    val sampleAd1 = Advertisement(
        uid = "1",
        groupId = "2",
        title = "aaaaaaaaa",
        images = list,
        description = "askooocood",
        price = "344",
        postDate = "13-10-2023")
    val sampleAd2 = Advertisement(
        uid = "2",
        groupId = "5",
        title = "bbbbbb",
        images = list,
        description = "askooocood",
        price = "344",
        postDate = "13-10-2023")
    val adListSample = listOf(sampleAd1, sampleAd1, sampleAd1, sampleAd1, sampleAd1, sampleAd1)
    val adListSample2 = listOf(sampleAd2, sampleAd2, sampleAd2, sampleAd2, sampleAd2, sampleAd2)
    HomeScreenContent(
        adListSample,
        adListSample2,
        adListSample,
        {},
        {})
}