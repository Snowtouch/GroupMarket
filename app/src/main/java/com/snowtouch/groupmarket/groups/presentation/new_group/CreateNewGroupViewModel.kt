package com.snowtouch.groupmarket.groups.presentation.new_group

import androidx.compose.runtime.mutableStateOf
import com.snowtouch.groupmarket.core.domain.model.Response
import com.snowtouch.groupmarket.core.presentation.GroupMarketViewModel
import com.snowtouch.groupmarket.core.presentation.util.SnackbarState
import com.snowtouch.groupmarket.groups.domain.repository.GroupsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateNewGroupViewModel(
    private val groupsRepository : GroupsRepository
): GroupMarketViewModel() {

    var uiState = mutableStateOf(CreateNewGroupUiState())

    private val _createGroupResponse = MutableStateFlow<Response<Boolean>>(Response.Success(false))
    val createGroupResponse: StateFlow<Response<Boolean>> = _createGroupResponse

    fun onNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        uiState.value = uiState.value.copy(description = newValue)
    }

    fun onPrivateGroupSwitchChange() {
        uiState.value = uiState.value.copy(isPrivate = !uiState.value.isPrivate)
    }

    fun createNewGroup(name: String, description: String) {
        launchCatching {
            _createGroupResponse.value = Response.Loading
            _createGroupResponse.value = groupsRepository.createNewGroup(name, description)
            if (_createGroupResponse.value is Response.Success) {
                uiState.value = uiState.value.copy(
                    name = "",
                    description = "",
                    isPrivate = false
                )
                showSnackbar(SnackbarState.DEFAULT, "Group created successfully")
            }
        }
    }
}