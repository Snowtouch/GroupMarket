package com.snowtouch.groupmarket.screens.group_ads

import com.snowtouch.groupmarket.model.Advertisement
import com.snowtouch.groupmarket.model.service.DatabaseService
import com.snowtouch.groupmarket.screens.GroupMarketViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GroupAdsScreenViewModel(private val databaseService: DatabaseService) : GroupMarketViewModel() {

    private val _rawAdvertisementsFlow = MutableStateFlow<List<Advertisement>>(emptyList())
    val advertisementsFlow: StateFlow<List<Advertisement>> = _rawAdvertisementsFlow

    fun fetchGroupAdvertisements(groupId: String) {
        launchCatching {
            val groupAdsList = databaseService.getGroupAdvertisementsList(groupId)
            _rawAdvertisementsFlow.value = groupAdsList
        }
    }
}