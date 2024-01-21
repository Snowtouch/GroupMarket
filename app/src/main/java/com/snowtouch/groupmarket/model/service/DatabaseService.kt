package com.snowtouch.groupmarket.model.service

import com.snowtouch.groupmarket.model.Advertisement
import kotlinx.coroutines.flow.Flow

interface DatabaseService {
    val advertisements: Flow<List<Advertisement>>

    suspend fun createAdvertisement(advertisement: Advertisement)
}