package com.snowtouch.groupmarket.groups.presentation.groups

import androidx.compose.runtime.mutableStateOf
import com.snowtouch.groupmarket.core.domain.model.Group
import com.snowtouch.groupmarket.core.domain.model.Response
import com.snowtouch.groupmarket.core.presentation.GroupMarketViewModel
import com.snowtouch.groupmarket.groups.domain.repository.GroupsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GroupsScreenViewModel(
    private val groupsRepository : GroupsRepository
): GroupMarketViewModel() {

    var uiState = mutableStateOf(GroupsScreenUiState())
        private set

    private val _initialDataResponse = MutableStateFlow<Response<List<Group>>>(Response.Success(emptyList()))
    val initialDataResponse: StateFlow<Response<List<Group>>> = _initialDataResponse

    init {
        getUserGroupsData()
    }

    private fun getUserGroupsData() {
        launchCatching {
            _initialDataResponse.value = Response.Loading
            val groupsResponse = groupsRepository.getUserGroups()

            if (groupsResponse is Response.Success) {
                val groupData = groupsResponse.data.orEmpty()

                val completeGroupsData = groupData.map { group ->
                    group.copy(
                        membersCount = getGroupMembersCount(group.uid!!),
                        advertisementsCount = getGroupAdvertisementsCount(group.uid)
                    )
                }

                _initialDataResponse.value = Response.Success(completeGroupsData)
            } else {
                _initialDataResponse.value = groupsResponse
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
