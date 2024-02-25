package com.snowtouch.groupmarket.screens.groups

import androidx.compose.runtime.mutableStateOf
import com.snowtouch.groupmarket.model.Advertisement
import com.snowtouch.groupmarket.model.Group
import com.snowtouch.groupmarket.model.User
import com.snowtouch.groupmarket.model.service.DatabaseService
import com.snowtouch.groupmarket.screens.GroupMarketViewModel
import com.snowtouch.groupmarket.screens.new_advertisement.NewAdvertisementUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GroupsScreenViewModel(
    private val databaseService: DatabaseService
): GroupMarketViewModel() {

    var uiState = mutableStateOf(GroupsScreenUiState())
        private set

    private val _userData: StateFlow<User?> = databaseService.userData
    val userData: StateFlow<User?> = _userData

    private val _userGroupsData: StateFlow<List<Group?>> = databaseService.userGroupsData
    val userGroupsData: StateFlow<List<Group?>> = _userGroupsData

    private val _rawAdvertisementsFlow = MutableStateFlow<List<Advertisement>>(emptyList())
    val advertisementsFlow: StateFlow<List<Advertisement>> = _rawAdvertisementsFlow

    init {
        fetchUserGroups()
    }

    private fun fetchUserGroups() {
        launchCatching {
            databaseService.getInitialUserGroupsData()
        }
    }

    fun fetchGroupAdvertisements(groupId: String) {
        launchCatching {
            val groupAdsList = databaseService.getGroupAdvertisementsList(groupId)
            _rawAdvertisementsFlow.value = groupAdsList
        }
    }
}
