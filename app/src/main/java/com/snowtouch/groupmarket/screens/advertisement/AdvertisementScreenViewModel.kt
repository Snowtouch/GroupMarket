package com.snowtouch.groupmarket.screens.advertisement

import androidx.lifecycle.SavedStateHandle
import com.snowtouch.groupmarket.screens.GroupMarketViewModel

internal class AdvertisementScreenViewModel(savedStateHandle: SavedStateHandle) : GroupMarketViewModel() {
    val advertisementArgs = AdvertisementArgs(savedStateHandle)
}