package com.snowtouch.feature_new_advertisement.presentation.components

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.snowtouch.core.presentation.components.CommonButton
import com.snowtouch.feature_new_advertisement.presentation.NewAdvertisementUiState

@Composable
fun NewAdvertisementContent(
    uiState: NewAdvertisementUiState,
    userGroupsList: List<Pair<String, String>>,
    onAdImagesChanged: (List<Uri>) -> Unit,
    onAdTitleChanged: (String) -> Unit,
    onAdDescriptionChanged: (String) -> Unit,
    onAdPriceChanged: (String) -> Unit,
    onUserGroupSelected: (String) -> Unit,
    onPostAdvertisementClick : () -> Unit,
    onSaveAsDraftClick : () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .wrapContentSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AdImagePicker(onImagesSelected = onAdImagesChanged)

        AdTitleCard(
            title = uiState.title,
            onNewValueTitle = onAdTitleChanged
        )
        AdDescriptionCard(
            description = uiState.description,
            onAdDescriptionChanged = onAdDescriptionChanged
        )
        AdPriceCard(
            price = uiState.price,
            onAdPriceChanged = onAdPriceChanged
        )
        SelectGroupDropdownMenu(
            userGroupsIdNamePairList = userGroupsList,
            onUserGroupSelected = onUserGroupSelected
        )
        CommonButton(
            onClick = onPostAdvertisementClick,
            text = "Post advertisement"
        )
    }
}