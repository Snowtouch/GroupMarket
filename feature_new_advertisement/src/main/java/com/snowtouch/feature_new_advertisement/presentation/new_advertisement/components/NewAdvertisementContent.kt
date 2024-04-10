package com.snowtouch.feature_new_advertisement.presentation.new_advertisement.components

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.snowtouch.core.presentation.components.CommonButton
import com.snowtouch.core.presentation.components.ext.adaptiveColumnWidth
import com.snowtouch.core.presentation.components.ext.cardContentPadding
import com.snowtouch.feature_new_advertisement.presentation.new_advertisement.UiState

@Composable
internal fun NewAdvertisementContent(
    uiState : UiState,
    userGroupsList : List<Map<String, String>>,
    onImagesChanged : (List<Uri>) -> Unit,
    onTitleChanged : (String) -> Unit,
    onDescriptionChanged : (String) -> Unit,
    onPriceChanged : (String) -> Unit,
    onGroupSelected : (String) -> Unit,
    onPostAdvertisementClick : () -> Unit,
    onSaveAsDraftClick : () -> Unit,
    modifier : Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .adaptiveColumnWidth()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AdImagePicker(onImagesSelected = onImagesChanged)

        ElevatedCard(
            modifier = Modifier.cardContentPadding()
        ) {
            TitleTextField(
                title = uiState.title,
                onNewValueTitle = onTitleChanged,
                isError = uiState.titleFieldError
            )
            DescriptionTextField(
                description = uiState.description,
                onDescriptionChanged = onDescriptionChanged,
                isError = uiState.descriptionFiledError
            )
            PriceTextField(
                price = uiState.price,
                onPriceChanged = onPriceChanged,
                isError = uiState.priceFieldError
            )
        }
        SelectGroupDropdownMenu(
            userGroupsIdNameMapList = userGroupsList,
            onUserGroupSelected = onGroupSelected,
            isError = uiState.groupFieldError
        )
        CommonButton(
            onClick = onPostAdvertisementClick,
            text = "Post advertisement"
        )
        CommonButton(
            onClick = onSaveAsDraftClick,
            text = "Save as draft"
        )
    }
}