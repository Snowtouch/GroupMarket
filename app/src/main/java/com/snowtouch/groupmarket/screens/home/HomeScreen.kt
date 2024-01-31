package com.snowtouch.groupmarket.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
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
    val userData = viewModel.userData.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState

}

@Composable
fun HomeScreenContent(
    newestAds: List<Advertisement>,
    favoritesAds: List<Advertisement>,
    recentlyWatchedAds: List<Advertisement>,
    onAdvertisementClick: () -> Unit
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Top
    ) {
        AdvertisementGrid(
            gridTitle = "Newest advertisements",
            advertisementList = newestAds,
            onAdvertisementClick = onAdvertisementClick
        )
    }
}
@Composable
fun AdvertisementGrid(
    gridTitle: String,
    advertisementList: List<Advertisement>,
    userFavorites: List<String> = emptyList(),
    onAdvertisementClick: () -> Unit
) {
    Text(text = gridTitle)
    LazyHorizontalStaggeredGrid(
        rows = StaggeredGridCells.Fixed(1)
    ) {items(advertisementList.size) { advertisement: Int ->
            AdvertisementCard(advertisementList[advertisement], userFavorites, onAdvertisementClick)
        }
    }
}

/*@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreenContent()
}*/