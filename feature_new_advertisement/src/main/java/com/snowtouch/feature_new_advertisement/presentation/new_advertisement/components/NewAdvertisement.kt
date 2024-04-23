package com.snowtouch.feature_new_advertisement.presentation.new_advertisement.components

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.core.presentation.components.CommonTopAppBar
import com.snowtouch.core.presentation.components.Loading
import com.snowtouch.core.presentation.components.LoadingFailed
import com.snowtouch.core.presentation.components.SinglePageScaffold
import com.snowtouch.feature_new_advertisement.presentation.advertisement_post_result.AdvertisementPostSuccess
import com.snowtouch.feature_new_advertisement.presentation.new_advertisement.ScreenState
import com.snowtouch.feature_new_advertisement.presentation.new_advertisement.UiState

@Composable
internal fun NewAdvertisement(
    uiState : UiState,
    onImagesChanged : (List<Uri>) -> Unit,
    onTitleChanged : (String) -> Unit,
    onDescriptionChanged : (String) -> Unit,
    onPriceChanged : (String) -> Unit,
    onGroupSelected : (String) -> Unit,
    onPostAdvertisementClick : () -> Unit,
    onFailedLoadingButtonClick : () -> Unit,
    navigateBack : () -> Unit,
    navigateToHome : () -> Unit,
    navigateToPostedAdvertisement : (String) -> Unit,
) {
    SinglePageScaffold(
        topBar = {
            CommonTopAppBar(
                title = "new advertisement",
                canNavigateBack = true,
                onNavigateBackClick = navigateBack
            )
        }
    ) { innerPadding ->
        when (uiState.screenState) {
            is ScreenState.Success -> NewAdvertisementContent(
                uiState = uiState,
                userGroupsList = uiState.groupIdNames,
                onImagesChanged = onImagesChanged,
                onTitleChanged = onTitleChanged,
                onDescriptionChanged = onDescriptionChanged,
                onPriceChanged = onPriceChanged,
                onGroupSelected = onGroupSelected,
                onPostAdvertisementClick = onPostAdvertisementClick,
                onSaveAsDraftClick = { /*TODO*/ },
                modifier = Modifier.padding(innerPadding)
            )

            is ScreenState.Loading -> Loading(modifier = Modifier.padding(innerPadding))
            is ScreenState.Uploading -> Loading(
                modifier = Modifier.padding(innerPadding),
                progress = uiState.screenState.progress.toFloat(),
                message = "Uploading image ${uiState.screenState.currentImageIndex} " +
                        "of ${uiState.screenState.imageCount}"
            )

            is ScreenState.AdPostSuccess -> AdvertisementPostSuccess(
                navigateToHome = navigateToHome,
                navigateToAdDetails = { navigateToPostedAdvertisement(uiState.newAdId) },
                modifier = Modifier.padding(innerPadding)
            )

            is ScreenState.Error -> LoadingFailed(
                canRefresh = true,
                onErrorIconClick = onFailedLoadingButtonClick,
                modifier = Modifier.padding(innerPadding),
                errorMessage = uiState.screenState.e.localizedMessage
            )
        }
    }

}