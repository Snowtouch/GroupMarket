package com.snowtouch.groupmarket.groups.presentation.new_group

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.groupmarket.core.presentation.components.ScaffoldTemplate
import com.snowtouch.groupmarket.groups.presentation.new_group.components.CreateNewGroup
import com.snowtouch.groupmarket.groups.presentation.new_group.components.CreateNewGroupTopBar

@Composable
fun CreateNewGroupScreen(
    onNavigateBackClick: () -> Unit
) {
    ScaffoldTemplate(
        topBar = {
            CreateNewGroupTopBar(
                onNavigateBackClick = onNavigateBackClick
            )
        }
    ) { innerPadding ->
        CreateNewGroup(modifier = Modifier.padding(innerPadding))
    }
}