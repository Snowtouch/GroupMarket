package com.snowtouch.feature_groups.presentation.new_group.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.core.domain.model.Response
import com.snowtouch.core.presentation.components.Loading
import com.snowtouch.core.presentation.util.SnackbarState
import com.snowtouch.feature_groups.presentation.new_group.CreateNewGroupViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateNewGroup(
    modifier : Modifier = Modifier,
    viewModel : CreateNewGroupViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState
    val createNewGroupResponse by viewModel.createGroupResponse.collectAsStateWithLifecycle()

    when (val newGroupResponse = createNewGroupResponse) {
        is Response.Loading -> Loading()
        is Response.Success -> CreateNewGroupContent(
            uiState = uiState,
            onGroupNameChanged = viewModel::onNameChange,
            onGroupDescriptionChanged = viewModel::onDescriptionChange,
            onPrivacySwitchChange = viewModel::onPrivateGroupSwitchChange ,
            onCreateGroupClick = { viewModel.createNewGroup(uiState.name,uiState.description) },
            modifier = modifier)
        is Response.Failure -> LaunchedEffect(Unit) {
            viewModel
                .showSnackbar(SnackbarState.ERROR, newGroupResponse.e.message ?: "Unknown error")
        }
    }
}