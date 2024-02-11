package com.snowtouch.groupmarket.screens.advertisement

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.snowtouch.groupmarket.model.service.DatabaseService
import com.snowtouch.groupmarket.screens.GroupMarketViewModel

internal class AdvertisementDetailScreenViewModel(
    private val  databaseService: DatabaseService
) : GroupMarketViewModel() {
}