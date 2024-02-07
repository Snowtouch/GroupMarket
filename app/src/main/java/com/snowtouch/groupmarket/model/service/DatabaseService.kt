package com.snowtouch.groupmarket.model.service

import com.snowtouch.groupmarket.model.Advertisement
import com.snowtouch.groupmarket.model.User
import kotlinx.coroutines.flow.StateFlow

interface DatabaseService {
    val userData: StateFlow<User?>
    suspend fun enableUserDataListener(onError: (Throwable?) -> Unit)
    suspend fun createNewUserData(user: User)
    suspend fun getNewAdReferenceKey(): String?
    suspend fun getUserGroupsNames(): List<String>
    suspend fun createAdvertisement(advertisement: Advertisement, ref: String)
    suspend fun getLatestAdvertisementsList(): List<Advertisement>
    suspend fun getUserFavoriteAdvertisementsList(): List<Advertisement>
    suspend fun addOrRemoveFavoriteAd(adId: String)
}