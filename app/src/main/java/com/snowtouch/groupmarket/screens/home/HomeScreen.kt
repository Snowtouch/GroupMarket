package com.snowtouch.groupmarket.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.snowtouch.groupmarket.common.composable.AdvertisementCard
import com.snowtouch.groupmarket.model.Advertisement
import com.snowtouch.groupmarket.model.User
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = koinViewModel()
){
    val
    HomeScreenContent()
}

@Composable
fun HomeScreenContent(
    newestAds: List<Advertisement>,
    favoritesAds: List<Advertisement>,
    recentlyWatchedAds: List<Advertisement>
) {
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Top
    ) {

    }
}
@Composable
fun AdvertisementGrid(
    gridTitle: String,
    advertisementList: List<Advertisement>,
    currentUser: User,
    openAdvertisement: () -> Unit
) {
    Text(text = gridTitle)
    LazyHorizontalStaggeredGrid(
        rows = StaggeredGridCells.Fixed(1)) {
        items(advertisementList.size) { item ->
            AdvertisementCard(advertisementList[item], currentUser, openAdvertisement)
        }
    }
}

/*@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreenContent()
}*/