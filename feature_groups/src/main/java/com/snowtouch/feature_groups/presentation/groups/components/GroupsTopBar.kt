package com.snowtouch.feature_groups.presentation.groups.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.core.presentation.components.CommonTopAppBar

@Composable
fun GroupsTopAppBar(
    onCreateGroupClick: () -> Unit,
    modifier : Modifier = Modifier,
    onNavigateBackClick: () -> Unit = {}
) {
    CommonTopAppBar(
        modifier = modifier,
        title = "Your groups",
        canNavigateBack = true,
        onNavigateBackClick = onNavigateBackClick,
        actions = {
            IconButton(onClick = onCreateGroupClick) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Create new group")
            }
        }
    )
}