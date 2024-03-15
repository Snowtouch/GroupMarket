package com.snowtouch.groupmarket.home.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.groupmarket.core.presentation.components.BottomNavigationBar
import com.snowtouch.groupmarket.core.presentation.components.NavigationRail
import com.snowtouch.groupmarket.core.presentation.components.ScaffoldTemplate
import com.snowtouch.groupmarket.core.presentation.util.DisplaySize
import com.snowtouch.groupmarket.home.presentation.components.Home
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    displaySize : DisplaySize,
    onNavigateToAdDetails: (String) -> Unit,
    onBottomBarIconClick: (String) -> Unit,
    viewModel: HomeViewModel = koinViewModel(),
){
    ScaffoldTemplate(
        bottomBar = {
            when (displaySize) {
                DisplaySize.Compact -> BottomNavigationBar(onNavItemClick = onBottomBarIconClick)
                DisplaySize.Extended -> NavigationRail(onNavItemClick = onBottomBarIconClick) }
        }
    ) { innerPadding ->
        Home(
            displaySize = displaySize,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

/*@Preview
@Composable
fun HomeScreenPreview() {
    val list: List<String> = emptyList()
    val sampleAd1 = Advertisement(
        ownerUid = "1",
        groupId = "2",
        title = "aaaaaaaaa",
        images = list,
        description = "askooocood",
        price = "344",
        postDate = "13-10-2023")
    val sampleAd2 = Advertisement(
        ownerUid = "2",
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
}*/