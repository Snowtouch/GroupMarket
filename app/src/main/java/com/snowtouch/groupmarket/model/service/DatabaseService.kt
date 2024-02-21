package com.snowtouch.groupmarket.model.service

import com.snowtouch.groupmarket.model.Advertisement
import com.snowtouch.groupmarket.model.Group
import com.snowtouch.groupmarket.model.User
import kotlinx.coroutines.flow.StateFlow

interface DatabaseService {

    val userData: StateFlow<User?>
    val userGroupsData: StateFlow<List<Group?>>

    suspend fun enableUserDataListener()

    suspend fun disableUserDataListener()

    suspend fun enableUserGroupsDataListeners()

    suspend fun disableUserGroupsDataListeners()

    suspend fun getNewAdReferenceKey(): String?

    suspend fun getAdvertisementDetails(advertisementUid: String): Advertisement

    suspend fun getLatestAdvertisementsList(): List<Advertisement>

    suspend fun getUserFavoriteAdvertisementsList(): List<Advertisement>

    suspend fun getUserGroupsNames(): List<String>

    suspend fun createNewUserData(user: User)

    suspend fun createAdvertisement(advertisement: Advertisement, ref: String)

    suspend fun createNewGroup(name: String, description: String, isPrivate: Boolean)

    suspend fun addOrRemoveFavoriteAd(adId: String)
}