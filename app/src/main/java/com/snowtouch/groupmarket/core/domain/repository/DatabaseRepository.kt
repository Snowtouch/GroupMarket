package com.snowtouch.groupmarket.core.domain.repository

import com.snowtouch.groupmarket.core.domain.model.Advertisement
import com.snowtouch.groupmarket.core.domain.model.Group
import com.snowtouch.groupmarket.core.domain.model.User
import kotlinx.coroutines.flow.StateFlow

interface DatabaseRepository {

    val userData: StateFlow<User?>
    val userGroupsData: StateFlow<List<Group?>>

    suspend fun getInitialUserGroupsData()

    suspend fun getInitialUserData()

    suspend fun enableUserDataListener()

    suspend fun disableUserDataListener()

    suspend fun enableUserGroupsDataListeners()

    suspend fun disableUserGroupsDataListeners()

    suspend fun getNewAdReferenceKey(): String?

    suspend fun getAdvertisementDetails(advertisementUid: String): Advertisement

    suspend fun getLatestAdvertisementsList(): List<Advertisement>

    suspend fun getGroupAdvertisementsList(groupId: String): List<Advertisement>

    suspend fun getUserFavoriteAdvertisementsList(): List<Advertisement>

    suspend fun createNewUserData(email: String, name: String)

    suspend fun createAdvertisement(advertisement: Advertisement, ref: String)

    suspend fun createNewGroup(name: String, description: String, isPrivate: Boolean)

    suspend fun toggleFavoriteAd(adId: String)
}