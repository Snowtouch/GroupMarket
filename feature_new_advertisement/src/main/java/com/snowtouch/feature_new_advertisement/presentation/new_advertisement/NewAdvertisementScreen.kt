package com.snowtouch.feature_new_advertisement.presentation.new_advertisement

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.core.presentation.components.CommonTopAppBar
import com.snowtouch.core.presentation.components.ScaffoldTemplate
import com.snowtouch.feature_new_advertisement.presentation.new_advertisement.components.NewAdvertisement

@Composable
fun NewAdvertisementScreen(
    navigateBack : () -> Unit,
    navigateToHome : () -> Unit,
    navigateToAdvertisement : (String) -> Unit,
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
            navigateToHome = navigateToHome,
            navigateToPostedAdvertisement = navigateToAdvertisement,
            modifier = Modifier.padding(innerPadding)
        )
    }
}