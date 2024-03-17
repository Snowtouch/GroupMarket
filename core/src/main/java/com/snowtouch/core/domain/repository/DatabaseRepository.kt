package com.snowtouch.core.domain.repository

import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Group
import com.snowtouch.core.domain.model.User
import kotlinx.coroutines.flow.StateFlow

interface DatabaseRepository {

    val userData: StateFlow<com.snowtouch.core.domain.model.User?>
    val userGroupsData: StateFlow<List<com.snowtouch.core.domain.model.Group?>>

    suspend fun getInitialUserGroupsData()

    suspend fun getInitialUserData()

    suspend fun enableUserDataListener()

    suspend fun disableUserDataListener()

    suspend fun enableUserGroupsDataListeners()

    suspend fun disableUserGroupsDataListeners()

    suspend fun getNewAdReferenceKey(): String?

    suspend fun getAdvertisementDetails(advertisementUid: String): com.snowtouch.core.domain.model.Advertisement

    suspend fun getLatestAdvertisementsList(): List<com.snowtouch.core.domain.model.Advertisement>

    suspend fun getGroupAdvertisementsList(groupId: String): List<com.snowtouch.core.domain.model.Advertisement>

    suspend fun getUserFavoriteAdvertisementsList(): List<com.snowtouch.core.domain.model.Advertisement>

    suspend fun createNewUserData(email: String, name: String)

    suspend fun createAdvertisement(advertisement: com.snowtouch.core.domain.model.Advertisement, ref: String)

    suspend fun createNewGroup(name: String, description: String, isPrivate: Boolean)

    suspend fun toggleFavoriteAd(adId: String)
}