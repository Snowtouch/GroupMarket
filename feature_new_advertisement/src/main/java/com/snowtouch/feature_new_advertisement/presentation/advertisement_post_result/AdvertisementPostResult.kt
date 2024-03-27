package com.snowtouch.feature_new_advertisement.presentation.advertisement_post_result

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.core.domain.model.Response
import com.snowtouch.core.presentation.components.Loading
import com.snowtouch.core.presentation.components.LoadingFailed
import com.snowtouch.feature_new_advertisement.presentation.ImageUploadUiState
import com.snowtouch.feature_new_advertisement.presentation.NewAdvertisementViewModel
import com.snowtouch.feature_new_advertisement.presentation.advertisement_post_result.components.AdvertisementPostResultContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun AdvertisementPostResult(
    navigateToHome : () -> Unit,
    navigateToAdDetails : (String) -> Unit,
    navigateBack : () -> Unit,
    modifier : Modifier = Modifier,
    viewmodel : NewAdvertisementViewModel = koinViewModel(),
) {
    val advertisementPostResponse by viewmodel.postAdvertisementResponse.collectAsStateWithLifecycle()
    val uploadUiState : ImageUploadUiState by viewmodel.uploadUiState.collectAsStateWithLifecycle()

    val uiState by viewmodel.uiState

    when (val state = uploadUiState) {
        is ImageUploadUiState.Uploading -> {
            Loading(
                modifier = modifier,
                progress = state.progress?.toFloat()
            )
        }
        is ImageUploadUiState.Success -> {
            LaunchedEffect(key1 = Unit) {
                viewmodel.postNewAdvertisement()
            }
            when (val advertisementPost = advertisementPostResponse) {
                is Response.Loading -> {
                    Loading(modifier = modifier)
                }
                is Response.Success -> {
                    AdvertisementPostResultContent(
                        onGoToHomeScreenClick = navigateToHome,
                        onGoToPostedAdDetailsClick = { navigateToAdDetails(uiState.newAdId) }
                    )
                }
                is Response.Failure -> {
                    LoadingFailed(
                        canRefresh = false,
                        onErrorIconClick = {},
                        modifier = modifier,
                        errorMessage = advertisementPost.e.localizedMessage
                    )
                }
            }
        }
        is ImageUploadUiState.Error -> {
            LoadingFailed(
                canRefresh = false,
                onErrorIconClick = navigateBack,
                modifier = Modifier,
                errorMessage = state.error
            )
        }
    }
}