package com.snowtouch.groupmarket.groups.presentation

import androidx.compose.runtime.mutableStateOf
import com.snowtouch.groupmarket.core.domain.model.AdvertisementPreview
import com.snowtouch.groupmarket.core.domain.model.Group
import com.snowtouch.groupmarket.core.domain.model.Response
import com.snowtouch.groupmarket.core.presentation.GroupMarketViewModel
import com.snowtouch.groupmarket.groups.domain.repository.GroupsRepository
import com.snowtouch.groupmarket.groups.presentation.groups.GroupsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GroupsViewModel(
    private val groupsRepository : GroupsRepository
): GroupMarketViewModel() {

    var uiState = mutableStateOf(GroupsUiState())
        private set

    private val _groupsDataResponse = MutableStateFlow<Response<List<Group>>>(Response.Success(emptyList()))
    val groupsDataResponse: StateFlow<Response<List<Group>>> = _groupsDataResponse

    private val _advertisementsResponse = MutableStateFlow<Response<List<AdvertisementPreview>>>(Response.Loading)
    val advertisementsResponse: StateFlow<Response<List<AdvertisementPreview>>> = _advertisementsResponse

    init {
        getUserGroupsData()
    }

    fun getGroupAdvertisements(groupId: String) {
        launchCatching {
            _advertisementsResponse.value = groupsRepository.getGroupAdvertisements(groupId)
        }
    }

    fun getUserGroupsData() {
        launchCatching {
            _groupsDataResponse.value = Response.Loading
            val groupsResponse = groupsRepository.getUserGroupsPreviewData()

            if (groupsResponse is Response.Success) {
                val groupData = groupsResponse.data.orEmpty()

                val completeGroupsData = groupData.map { group ->
                    group.copy(
                        membersCount = getGroupMembersCount(group.uid!!),
                        advertisementsCount = getGroupAdvertisementsCount(group.uid)
                    )
                }

                _groupsDataResponse.value = Response.Success(completeGroupsData)
            } else {
                _groupsDataResponse.value = groupsResponse
            }
        }
    }

    private fun getGroupAdvertisementsCount(groupId : String) : Int {
        var count: Int? = null

        launchCatching {
            val adsCountResponse = groupsRepository.getGroupAdsCount(groupId)
            if (adsCountResponse is Response.Success) {
                count = adsCountResponse.data
            }
        }
        return  count ?: 0
    }

    private fun getGroupMembersCount(groupId: String) : Int {
        var count: Int? = null

        launchCatching {
            val membersCountResponse = groupsRepository.getGroupUsersCount(groupId)
            if (membersCountResponse is Response.Success) {
                count = membersCountResponse.data
            }
        }
        return count ?: 0
    }
}
