package com.snowtouch.feature_groups.domain.repository

import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Result
import com.snowtouch.feature_groups.domain.model.GroupPreview
import kotlinx.coroutines.flow.Flow

interface GroupsRepository {

    fun getUserGroupsPreviewData() : Flow<Result<List<GroupPreview>>>

    suspend fun createNewGroup(name: String, description: String) : Result<Boolean>

    fun getGroupAdvertisements(groupId: String) : Flow<Result<List<AdvertisementPreview>>>
}