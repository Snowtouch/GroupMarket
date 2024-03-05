package com.snowtouch.groupmarket.groups.domain.repository

import com.snowtouch.groupmarket.core.domain.model.Advertisement
import com.snowtouch.groupmarket.core.domain.model.Group
import com.snowtouch.groupmarket.core.domain.model.Response

interface GroupsRepository {

    suspend fun getUserGroups(userId: String) : Response<List<Group>>

    suspend fun createNewGroup() : Response<Boolean>

    suspend fun getGroupAdvertisements(groupId: String) : Response<List<Advertisement>>
}