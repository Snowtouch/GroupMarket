package com.snowtouch.feature_new_advertisement.presentation.new_advertisement.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.snowtouch.feature_new_advertisement.presentation.NewAdvertisementViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewAdvertisement(
    navigateToPostResult : () -> Unit,
    modifier : Modifier = Modifier,
    viewModel : NewAdvertisementViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState

    NewAdvertisementContent(
        uiState = uiState,
        userGroupsList = emptyList(),
        onAdImagesChanged = viewModel::onImagesUriChange,
        onAdTitleChanged = viewModel::onTitleChange,
        onAdDescriptionChanged = viewModel::onDescriptionChange,
        onAdPriceChanged = viewModel::onPriceChange,
        onUserGroupSelected = viewModel::onAdGroupSelected,
        onPostAdvertisementClick = {
            viewModel.postNewAdvertisement()
            navigateToPostResult() },
        onSaveAsDraftClick = {},
        modifier = modifier
    )
}