package com.snowtouch.groupmarket.screens.advertisement

import com.snowtouch.groupmarket.model.Advertisement
import com.snowtouch.groupmarket.model.service.DatabaseService
import com.snowtouch.groupmarket.screens.GroupMarketViewModel

internal class AdvertisementDetailScreenViewModel(
    private val  databaseService: DatabaseService
) : GroupMarketViewModel() {

    fun getAdvertisementDetails(advertisementUid: String): Advertisement {

        var adDetails = Advertisement()

        launchCatching {
            adDetails = databaseService.getAdvertisementDetails(advertisementUid)
        }
        return adDetails
    }
}