package com.snowtouch.feature_new_advertisement.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.core.presentation.components.CommonTopAppBar
import com.snowtouch.core.presentation.components.ScaffoldTemplate
import com.snowtouch.feature_new_advertisement.presentation.components.NewAdvertisement

@Composable
fun NewAdvertisementScreen(
    navigateBack : () -> Unit,
) {
    ScaffoldTemplate(
        topBar = {
            CommonTopAppBar(
                title = "new advertisement",
                canNavigateBack = true,
                onNavigateBackClick = navigateBack
            )
        }
    ) { innerPadding ->
        NewAdvertisement(
            modifier = Modifier.padding(innerPadding)
        )
    }
}