package com.snowtouch.feature_new_advertisement.presentation.new_advertisement.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.core.presentation.components.Loading
import com.snowtouch.core.presentation.components.LoadingFailed
import com.snowtouch.feature_new_advertisement.presentation.NewAdvertisementViewModel
import com.snowtouch.feature_new_advertisement.presentation.advertisement_post_result.AdvertisementPostSuccess
import com.snowtouch.feature_new_advertisement.presentation.new_advertisement.NewAdUiState
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun NewAdvertisement(
    navigateToHome : () -> Unit,
    navigateToPostedAdvertisement : (String) -> Unit,
    modifier : Modifier = Modifier,
    viewModel : NewAdvertisementViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    when (val state = uiState) {
        is NewAdUiState.EditingNewAd -> NewAdvertisementContent(
            uiState = state,
            userGroupsList = state.groupIdNames,
            onImagesChanged = viewModel::onImagesUriChange,
            onTitleChanged = viewModel::onTitleChange,
            onDescriptionChanged = viewModel::onDescriptionChange,
            onPriceChanged = viewModel::onPriceChange,
            onGroupSelected = viewModel::onUserGroupSelected,
            onPostAdvertisementClick = { viewModel.postNewAdvertisement(context) },
            onSaveAsDraftClick = { /*TODO*/ },
            modifier = modifier
        )

        is NewAdUiState.Loading -> Loading(modifier = modifier)
        is NewAdUiState.Uploading -> Loading(
            modifier = modifier,
            progress = state.progress.toFloat(),
            message = "Uploading image ${state.currentImageIndex} of ${state.imageCount}"
        )

        is NewAdUiState.Success -> AdvertisementPostSuccess(
            navigateToHome = navigateToHome,
            navigateToAdDetails = { navigateToPostedAdvertisement(state.newAdId) },
            modifier = modifier
        )

        is NewAdUiState.Error -> LoadingFailed(
            canRefresh = true,
            onErrorIconClick = { viewModel.getUserGroupsIdNamePairs() },
            modifier = modifier,
            errorMessage = state.e.localizedMessage
        )
    }
}