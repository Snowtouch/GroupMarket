package com.snowtouch.feature_new_advertisement.presentation.new_advertisement

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.feature_new_advertisement.presentation.NewAdvertisementViewModel
import com.snowtouch.feature_new_advertisement.presentation.new_advertisement.components.NewAdvertisement

@Composable
internal fun NewAdvertisementScreen(
    navigateBack : () -> Unit,
    navigateToHome : () -> Unit,
    navigateToAdvertisement : (String) -> Unit,
    viewModel : NewAdvertisementViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getUserGroupsIdNamePairs()
    }

    NewAdvertisement(
        uiState = uiState,
        onImagesChanged = viewModel::onImagesUriChange,
        onTitleChanged = viewModel::onTitleChange,
        onDescriptionChanged = viewModel::onDescriptionChange,
        onPriceChanged = viewModel::onPriceChange,
        onGroupSelected = viewModel::onUserGroupSelected,
        onPostAdvertisementClick = { viewModel.postAdvertisement(context) },
        onFailedLoadingButtonClick = viewModel::getUserGroupsIdNamePairs,
        navigateBack = navigateBack,
        navigateToHome = navigateToHome,
        navigateToPostedAdvertisement = navigateToAdvertisement,
    )
}