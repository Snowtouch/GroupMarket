package com.snowtouch.groupmarket.model.service.impl

import com.snowtouch.groupmarket.model.Advertisement
import com.snowtouch.groupmarket.model.service.DatabaseService
import kotlinx.coroutines.flow.Flow

class DatabaseServiceImpl : DatabaseService {
    override val advertisements: Flow<List<Advertisement>>
        get() = TODO("Not yet implemented")

    override suspend fun createAdvertisement(advertisement: Advertisement) {
        TODO("Not yet implemented")
    }
}