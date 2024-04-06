package com.snowtouch.feature_groups.presentation.new_group

import androidx.compose.runtime.mutableStateOf
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.presentation.GroupMarketViewModel
import com.snowtouch.core.presentation.util.SnackbarState
import com.snowtouch.feature_groups.domain.repository.GroupsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateNewGroupViewModel(
    private val groupsRepository : GroupsRepository
): GroupMarketViewModel() {

    var uiState = mutableStateOf(CreateNewGroupUiState())

    private val _createGroupResult = MutableStateFlow<Result<Boolean>>(Result.Success(false))
    val createGroupResult: StateFlow<Result<Boolean>> = _createGroupResult

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
            _createGroupResult.value = Result.Loading
            _createGroupResult.value = groupsRepository.createNewGroup(name, description)
            if (_createGroupResult.value is Result.Success) {
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