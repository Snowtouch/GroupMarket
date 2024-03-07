package com.snowtouch.groupmarket.groups.presentation.groups

import androidx.compose.runtime.mutableStateOf
import com.snowtouch.groupmarket.core.domain.model.Group
import com.snowtouch.groupmarket.core.presentation.GroupMarketViewModel
import com.snowtouch.groupmarket.groups.domain.repository.GroupsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GroupsScreenViewModel(
    private val groupsRepository : GroupsRepository
): GroupMarketViewModel() {

    var uiState = mutableStateOf(GroupsScreenUiState())
        private set

    private val _userGroupsData: StateFlow<List<Group?>> = MutableStateFlow<List<Group>>(emptyList())
    val userGroupsData: StateFlow<List<Group?>> = _userGroupsData

    init {

    }

    private fun getUserGroupsData() {

    }
}
