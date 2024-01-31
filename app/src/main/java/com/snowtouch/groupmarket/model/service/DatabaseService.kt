package com.snowtouch.groupmarket.model.service

import com.snowtouch.groupmarket.model.Advertisement
import com.snowtouch.groupmarket.model.User
import kotlinx.coroutines.flow.StateFlow

interface DatabaseService {
    val userData: StateFlow<User?>
    suspend fun enableUserDataListener(onError: (Throwable?) -> Unit)
    suspend fun createAdvertisement(advertisement: Advertisement)
    suspend fun getLatestAdvertisementsList() : List<Advertisement>
}