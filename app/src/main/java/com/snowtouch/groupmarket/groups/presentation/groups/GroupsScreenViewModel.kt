package com.snowtouch.groupmarket.groups.presentation.groups

import androidx.compose.runtime.mutableStateOf
import com.snowtouch.groupmarket.core.domain.model.Advertisement
import com.snowtouch.groupmarket.core.domain.model.Group
import com.snowtouch.groupmarket.core.domain.model.User
import com.snowtouch.groupmarket.core.domain.repository.DatabaseRepository
import com.snowtouch.groupmarket.core.presentation.GroupMarketViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GroupsScreenViewModel(
    private val databaseRepository: DatabaseRepository
): GroupMarketViewModel() {

    var uiState = mutableStateOf(GroupsScreenUiState())
        private set

    private val _userData: StateFlow<User?> = databaseRepository.userData
    val userData: StateFlow<User?> = _userData

    private val _userGroupsData: StateFlow<List<Group?>> = databaseRepository.userGroupsData
    val userGroupsData: StateFlow<List<Group?>> = _userGroupsData

    private val _rawAdvertisementsFlow = MutableStateFlow<List<Advertisement>>(emptyList())
    val advertisementsFlow: StateFlow<List<Advertisement>> = _rawAdvertisementsFlow

    init {
        fetchUserGroups()
    }

    private fun fetchUserGroups() {
        launchCatching {
            databaseRepository.getInitialUserGroupsData()
        }
    }

    fun fetchGroupAdvertisements(groupId: String) {
        launchCatching {
            val groupAdsList = databaseRepository.getGroupAdvertisementsList(groupId)
            _rawAdvertisementsFlow.value = groupAdsList
        }
    }
}
