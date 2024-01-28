package com.snowtouch.groupmarket.screens.new_advertisement

import androidx.lifecycle.viewModelScope
import com.snowtouch.groupmarket.model.Advertisement
import com.snowtouch.groupmarket.model.service.DatabaseService
import com.snowtouch.groupmarket.screens.GroupMarketViewModel

class NewAdvertisementScreenViewModel(private val databaseService: DatabaseService): GroupMarketViewModel() {

    fun onPostNewAdvertisementClick(advertisement: Advertisement) {
        launchCatching {
            databaseService.createAdvertisement(advertisement)
        }
    }
}