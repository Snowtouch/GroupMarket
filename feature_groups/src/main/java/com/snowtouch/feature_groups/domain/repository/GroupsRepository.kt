package com.snowtouch.feature_groups.domain.repository

import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Group
import com.snowtouch.core.domain.model.Response

interface GroupsRepository {

    suspend fun getUserGroupsPreviewData() : Response<List<Group>>

    suspend fun getGroupUsersCount(groupId: String) : Response<Int>

    suspend fun getGroupAdsCount(groupId : String) : Response<Int>

    suspend fun createNewGroup(name: String, description: String) : Response<Boolean>

    suspend fun getGroupAdvertisements(groupId: String) : Response<List<AdvertisementPreview>>
}