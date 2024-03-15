package com.snowtouch.groupmarket.groups.presentation.new_group.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.groupmarket.core.presentation.components.CommonTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewGroupTopBar(
    modifier : Modifier = Modifier,
    onNavigateBackClick: () -> Unit = {}
) {
    CommonTopAppBar(
        modifier = modifier,
        title = "Create new group",
        canNavigateBack = true,
        onNavigateBackClick = onNavigateBackClick
    )
}