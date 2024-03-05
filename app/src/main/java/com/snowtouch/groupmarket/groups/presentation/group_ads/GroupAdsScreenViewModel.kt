package com.snowtouch.groupmarket.groups.presentation.group_ads

import com.snowtouch.groupmarket.core.domain.model.Advertisement
import com.snowtouch.groupmarket.core.domain.repository.DatabaseRepository
import com.snowtouch.groupmarket.core.presentation.GroupMarketViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GroupAdsScreenViewModel(private val databaseRepository: DatabaseRepository) : GroupMarketViewModel() {

    private val _rawAdvertisementsFlow = MutableStateFlow<List<Advertisement>>(emptyList())
    val advertisementsFlow: StateFlow<List<Advertisement>> = _rawAdvertisementsFlow

    fun fetchGroupAdvertisements(groupId: String) {
        launchCatching {
            val groupAdsList = databaseRepository.getGroupAdvertisementsList(groupId)
            _rawAdvertisementsFlow.value = groupAdsList
        }
    }
}