package com.snowtouch.groupmarket.screens.home

import androidx.compose.runtime.mutableStateOf
import com.snowtouch.groupmarket.model.Advertisement
import com.snowtouch.groupmarket.model.User
import com.snowtouch.groupmarket.model.service.DatabaseService
import com.snowtouch.groupmarket.screens.GroupMarketViewModel
import kotlinx.coroutines.flow.StateFlow

class HomeScreenViewModel(private val databaseService: DatabaseService) : GroupMarketViewModel() {
    private val _userData: StateFlow<User?> = databaseService.userData
    val userData: StateFlow<User?> = _userData

    var uiState = mutableStateOf(HomeScreenUiState())
        private set

    init {
        fetchLatestAdvertisements()
        fetchUserFavoriteAdvertisements()
    }
    fun favoriteAdToggle(adId: String) {
        launchCatching{
            databaseService.addOrRemoveFavoriteAd(adId)
        }
    }
    private fun fetchUserFavoriteAdvertisements() {
        launchCatching {
            val userFavoriteAds = databaseService.getUserFavoriteAdvertisementsList()
            uiState.value = uiState.value.copy(userFavoriteAds = userFavoriteAds)
        }
    }
    private fun fetchLatestAdvertisements() {
         launchCatching {
             val newestAdvertisements = databaseService.getLatestAdvertisementsList()
             uiState.value = uiState.value.copy(newestAds = newestAdvertisements)
        }
    }
}
data class HomeScreenUiState(
    val newestAds: List<Advertisement> = emptyList(),
    val userFavoriteAds: List<Advertisement> = emptyList(),
    val userRecentlyWatchedAds: List<Advertisement> = emptyList()
)