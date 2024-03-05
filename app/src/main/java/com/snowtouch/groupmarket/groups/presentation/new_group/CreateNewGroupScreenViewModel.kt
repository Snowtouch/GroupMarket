package com.snowtouch.groupmarket.groups.presentation.new_group

import androidx.compose.runtime.mutableStateOf
import com.snowtouch.groupmarket.core.domain.repository.DatabaseRepository
import com.snowtouch.groupmarket.core.presentation.GroupMarketViewModel

class CreateNewGroupScreenViewModel(
    private val databaseRepository: DatabaseRepository
): GroupMarketViewModel() {

    var uiState = mutableStateOf(CreateNewGroupUiState())

    fun onNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        uiState.value = uiState.value.copy(description = newValue)
    }

    fun onPrivateGroupSwitchChange() {
        uiState.value = uiState.value.copy(isPrivate = !uiState.value.isPrivate)
    }

    fun createNewGroup() {
        launchCatching {
            databaseRepository.createNewGroup(
                name = uiState.value.name,
                description = uiState.value.description,
                isPrivate = uiState.value.isPrivate
            )
            databaseRepository.enableUserGroupsDataListeners()
        }
    }
}