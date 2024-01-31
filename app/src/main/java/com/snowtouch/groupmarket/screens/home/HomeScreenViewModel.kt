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
        getLatestAdvertisements()
    }

    private fun getLatestAdvertisements() {
         launchCatching {
             val latestAdvertisements = databaseService.getLatestAdvertisementsList()
             uiState.value = uiState.value.copy(newestAdvertisements = latestAdvertisements)
        }
    }
}
data class HomeScreenUiState(
    val newestAdvertisements: List<Advertisement> = emptyList()
)