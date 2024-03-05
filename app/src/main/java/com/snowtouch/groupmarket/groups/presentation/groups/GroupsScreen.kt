package com.snowtouch.groupmarket.groups.presentation.groups

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.groupmarket.core.domain.model.Group
import com.snowtouch.groupmarket.core.presentation.components.BottomNavigationBar
import com.snowtouch.groupmarket.core.presentation.components.DisplaySize
import com.snowtouch.groupmarket.core.presentation.components.NavigationRail
import com.snowtouch.groupmarket.core.presentation.components.ScaffoldTemplate
import com.snowtouch.groupmarket.groups.presentation.group_ads.GroupAdsScreenContent
import com.snowtouch.groupmarket.core.presentation.components.theme.GroupMarketTheme
import com.snowtouch.groupmarket.groups.presentation.components.GroupCard
import com.snowtouch.groupmarket.groups.presentation.components.GroupsTopAppBar

@Composable
fun GroupsScreen(
    viewModel: GroupsScreenViewModel,
    displaySize : DisplaySize,
    onBottomBarIconClick: (String) -> Unit,
    navigateToGroupAdsScreen: (String) -> Unit,
    navigateToNewGroupScreen: () -> Unit,
    navigateToAdDetailsScreen: (String) -> Unit
) {
    val userGroupsData by viewModel.userGroupsData.collectAsStateWithLifecycle()
    val groupAdsData by viewModel.advertisementsFlow.collectAsStateWithLifecycle()

    var selectedGroupId by remember {
        mutableStateOf("")
    }

    ScaffoldTemplate(
        topBar = { GroupsTopAppBar(onAddGroupClick = navigateToNewGroupScreen) },
        bottomBar = {
            when (displaySize) {
                DisplaySize.Compact -> BottomNavigationBar(onNavItemClick = onBottomBarIconClick)
                DisplaySize.Extended -> NavigationRail(onNavItemClick = onBottomBarIconClick) }
        }
    ) {
        Row {
            GroupsScreenContent(
                userGroupsList = userGroupsData,
                onGoToGroupAdsClick = { groupId ->
                    if (isScreenSizeCompact) {
                        navigateToGroupAdsScreen(groupId)
                    }
                    else {
                        selectedGroupId = groupId
                        viewModel.fetchGroupAdvertisements(selectedGroupId)
                    }
                },
                onCreateNewGroupClick = navigateToNewGroupScreen
            )
            AnimatedVisibility(visible = !isScreenSizeCompact && selectedGroupId.isNotEmpty()) {
                GroupAdsScreenContent(
                    groupId = selectedGroupId,
                    groupAdList = groupAdsData,
                    onAdCardClick = navigateToAdDetailsScreen
                )
            }
        }
    }
}

@Composable
fun GroupsScreenContent(
    userGroupsList: List<Group>,
    onGoToGroupAdsClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .sizeIn(minWidth = 200.dp,maxWidth = 600.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(items = userGroupsList) { group ->
                GroupCard(
                    groupId = group.uid ?: "",
                    groupName = group.name ?: "",
                    groupOwner = group.ownerName ?: "",
                    groupDescription = group.description ?: "",
                    numberOfAdvertisements = group.advertisements?.size?: 0,
                    numberOfMembers = group.members?.size!!,
                    onGoToGroupAdsClick = onGoToGroupAdsClick
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@PreviewScreenSizes
@Composable
fun GroupsScreenContentPreview() {
    GroupMarketTheme(dynamicColor = false) {
        val previewGroup = Group(
            uid = "s34s4x2rf356",
            ownerId = "kiOoB6StvIc8I9vj",
            ownerName = "Max",
            members = emptyMap(),
            name = "Default group",
            description = "Description text",
            advertisements = null
        )
        val previewGroupList =
            listOf(previewGroup, previewGroup, previewGroup, previewGroup, previewGroup)
        GroupsScreenContent(previewGroupList, {}, {})
    }
}