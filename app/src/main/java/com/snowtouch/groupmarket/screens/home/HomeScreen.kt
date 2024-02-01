package com.snowtouch.groupmarket.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
        onFavoriteButtonClick = viewModel.
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
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Top
    ) {
        AdvertisementGrid(
            gridTitle = "Newest advertisements",
            advertisementList = newestAds,
            onAdvertisementClick = onAdvertisementClick,
            onFavoriteButtonClick = onFavoriteButtonClick
        )
        Divider()
        AdvertisementGrid(
            gridTitle = "Your favorites",
            advertisementList = favoritesAds,
            onAdvertisementClick = onAdvertisementClick,
            onFavoriteButtonClick = onFavoriteButtonClick
        )
        AdvertisementGrid(
            gridTitle = "Recently watched",
            advertisementList = recentlyWatchedAds,
            onAdvertisementClick = onAdvertisementClick,
            onFavoriteButtonClick = onFavoriteButtonClick
        )
    }
}
@Composable
fun AdvertisementGrid(
    gridTitle: String,
    advertisementList: List<Advertisement>,
    userFavorites: List<String> = emptyList(),
    onAdvertisementClick: () -> Unit,
    onFavoriteButtonClick: (String) -> Unit
) {
    Text(text = gridTitle)
    LazyHorizontalStaggeredGrid(rows = StaggeredGridCells.Fixed(1)
    ) {items(advertisementList) { advertisement ->
            AdvertisementCard(
                advertisement = advertisement,
                favoritesList = userFavorites,
                onCardClick = onAdvertisementClick,
                onFavoriteButtonClick = onFavoriteButtonClick
            )
        }
    }
}

/*@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreenContent()
}*/