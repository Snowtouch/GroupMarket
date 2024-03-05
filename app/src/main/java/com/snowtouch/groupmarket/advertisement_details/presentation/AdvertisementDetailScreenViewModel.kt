package com.snowtouch.groupmarket.advertisement_details.presentation

import com.snowtouch.groupmarket.core.domain.model.Advertisement
import com.snowtouch.groupmarket.core.domain.repository.DatabaseRepository
import com.snowtouch.groupmarket.core.presentation.GroupMarketViewModel

internal class AdvertisementDetailScreenViewModel(
    private val  databaseRepository: DatabaseRepository
) : GroupMarketViewModel() {

    fun getAdvertisementDetails(advertisementUid: String): Advertisement {

        var adDetails = Advertisement()

        launchCatching {
            adDetails = databaseRepository.getAdvertisementDetails(advertisementUid)
        }
        return adDetails
    }
}