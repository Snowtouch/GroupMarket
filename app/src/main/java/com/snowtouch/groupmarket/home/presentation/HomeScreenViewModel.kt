package com.snowtouch.groupmarket.home.presentation

import androidx.compose.runtime.mutableStateOf
import com.snowtouch.groupmarket.core.domain.model.Advertisement
import com.snowtouch.groupmarket.core.domain.model.User
import com.snowtouch.groupmarket.core.domain.repository.DatabaseRepository
import com.snowtouch.groupmarket.core.presentation.GroupMarketViewModel
import kotlinx.coroutines.flow.StateFlow

class HomeScreenViewModel(private val databaseRepository: DatabaseRepository) : GroupMarketViewModel() {

    private val _userData: StateFlow<User?> = databaseRepository.userData
    val userData: StateFlow<User?> = _userData


    var uiState = mutableStateOf(HomeScreenUiState())
        private set

    init {
        fetchLatestAdvertisements()
        fetchUserFavoriteAdvertisements()
    }
    fun favoriteAdToggle(adId: String) {
        launchCatching{
            databaseRepository.toggleFavoriteAd(adId)
        }
    }
    private fun fetchUserFavoriteAdvertisements() {
        launchCatching {
            val userFavoriteAds = databaseRepository.getUserFavoriteAdvertisementsList()
            uiState.value = uiState.value.copy(userFavoriteAds = userFavoriteAds)
        }
    }
    private fun fetchLatestAdvertisements() {
         launchCatching {
             val newestAdvertisements = databaseRepository.getLatestAdvertisementsList()
             uiState.value = uiState.value.copy(newestAds = newestAdvertisements)
        }
    }
}
data class HomeScreenUiState(
    val newestAds: List<Advertisement> = emptyList(),
    val userFavoriteAds: List<Advertisement> = emptyList(),
    val userRecentlyWatchedAds: List<Advertisement> = emptyList()
)