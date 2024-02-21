package com.snowtouch.groupmarket.screens.groups

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.groupmarket.common.composable.VerticalDivider
import com.snowtouch.groupmarket.common.ext.cardContentPadding
import com.snowtouch.groupmarket.model.Group
import org.koin.androidx.compose.koinViewModel

@Composable
fun GroupsScreen(
    viewModel: GroupsScreenViewModel = koinViewModel(),
    navigateToGroupAdsScreen: (String) -> Unit,
    navigateToNewGroupScreen: () -> Unit
) {

    val userData by viewModel.userData.collectAsStateWithLifecycle()
    val userGroupsData by viewModel.userGroupsData.collectAsStateWithLifecycle()

    GroupsScreenContent(
        userGroupsList = userGroupsData,
        onGoToGroupAdsClick = navigateToGroupAdsScreen,
        onCreateNewGroupClick = navigateToNewGroupScreen
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreenContent(
    userGroupsList: List<Group?>,
    onGoToGroupAdsClick: (String) -> Unit,
    onCreateNewGroupClick: () -> Unit
) {
    Column {
        TopAppBar(
            title = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(text = "Your groups") } },
            actions = {
                IconButton(onClick = onCreateNewGroupClick) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Create new group") }})
        if (!userGroupsList.contains(null)){
            LazyColumn {
                items(items = userGroupsList) { group ->
                    GroupCard(group = group!!, onGroupDetailsClick = onGoToGroupAdsClick)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupCard(
    modifier: Modifier = Modifier,
    group: Group,
    onGroupDetailsClick: (String) -> Unit

) {
    var expanded by remember { mutableStateOf(false) }

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp),
        onClick = { expanded = !expanded },
        shape = MaterialTheme.shapes.small
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = modifier
                    .height(IntrinsicSize.Min)
                    .cardContentPadding(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier.weight(1f)) {
                    Text(
                        text = group.name,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(text = "Owner: ${group.ownerName}")
                }
                Column(modifier.padding(4.dp)) {
                    Text(text = "Advertisements: ${group.advertisements?.size ?: "0"}")
                    Text(text = "Members: ${group.members.size}")
                }
                Box {
                    VerticalDivider()
                    IconButton(
                        onClick = { onGroupDetailsClick(group.uid) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = "View details"
                        )
                    }
                }
            }
            AnimatedVisibility(visible = expanded) {
                Column {
                    Text(text = group.description)
                }
            }
            Divider()
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier
                    .rotate(if (!expanded) 0f else 180f))
        }
    }
}

@Preview
@Composable
fun GroupsScreenContentPreview() {
    val previewGroup = Group(
        uid = "s34s4x2rf356",
        ownerId = "kiOoB6StvIc8I9vjP8DSTfMNB2D9",
        ownerName = "Max",
        members = emptyList(),
        name = "Default group",
        description = "Description text",
        advertisements = null
    )
    val previewGroupList = listOf(previewGroup, previewGroup, previewGroup, previewGroup, previewGroup)
    GroupsScreenContent(previewGroupList, {}) {}
}
@Preview
@Composable
fun GroupCardPreview() {
    GroupCard(group = Group(
        uid = "s34s4x2rf356",
        ownerId = "kiOoB6StvIc8I9vjP8DSTfMNB2D9",
        ownerName = "Max",
        members = emptyList(),
        name = "Default group",
        description = "Description text",
        advertisements = null
    ), onGroupDetailsClick = {})
}
@Preview
@Composable
fun GroupListPreview() {
    val previewGroup = Group(
        uid = "s34s4x2rf356",
        ownerId = "kiOoB6StvIc8I9vjP8DSTfMNB2D9",
        ownerName = "Max",
        members = emptyList(),
        name = "Default group",
        description = "Description text",
        advertisements = null
    )
    val previewGroupList = listOf(previewGroup, previewGroup, previewGroup, previewGroup, previewGroup)
    GroupsScreenContent(
        userGroupsList = previewGroupList,
        onGoToGroupAdsClick = {}
    ) {}
}