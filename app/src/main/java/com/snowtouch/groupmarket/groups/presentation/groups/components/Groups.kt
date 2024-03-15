package com.snowtouch.groupmarket.groups.presentation.groups.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.groupmarket.core.domain.model.Response
import com.snowtouch.groupmarket.core.presentation.components.Loading
import com.snowtouch.groupmarket.core.presentation.components.theme.LoadingFailed
import com.snowtouch.groupmarket.core.presentation.util.DisplaySize
import com.snowtouch.groupmarket.groups.presentation.GroupsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Groups(
    displaySize : DisplaySize,
    onGoToGroupAdsClick: (String) -> Unit,
    onAdvertisementCardClick:  (String) -> Unit,
    modifier : Modifier = Modifier,
    viewModel: GroupsViewModel = koinViewModel()
) {

    val groupsDataResponse by viewModel.groupsDataResponse.collectAsStateWithLifecycle()

    when (val groupsResponse = groupsDataResponse) {
        is Response.Loading -> Loading(modifier = modifier)

        is Response.Success ->
            GroupsContent(
                userGroupsList = groupsResponse.data ?: emptyList(),
                displaySize = displaySize,
                onGoToGroupAdsClick = onGoToGroupAdsClick,
                onAdvertisementCardClick = onAdvertisementCardClick,
                modifier = modifier
            )

        is Response.Failure -> LoadingFailed(
            onRefreshClick = viewModel::getUserGroupsData,
            modifier = modifier,
            errorMessage = groupsResponse.e.localizedMessage
        )
    }
}