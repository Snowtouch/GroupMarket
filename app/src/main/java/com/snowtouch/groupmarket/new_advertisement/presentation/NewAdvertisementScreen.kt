

package com.snowtouch.groupmarket.new_advertisement.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.snowtouch.groupmarket.R
import com.snowtouch.groupmarket.auth.presentation.components.ValidatedTextFieldOnCard
import com.snowtouch.groupmarket.core.presentation.components.CommonButton
import com.snowtouch.groupmarket.core.presentation.components.ext.cardContentPadding

@Composable
fun NewAdvertisementScreen(
    modifier: Modifier = Modifier,
    viewModel: NewAdvertisementScreenViewModel,
    onPostAdvertisementClick: () -> Unit
) {

    val uiState by viewModel.uiState
    val uploadState by viewModel.uploadState.collectAsStateWithLifecycle()
    val groupsIdNamePairList by viewModel.groupsIdNamePairList.collectAsStateWithLifecycle()

    NewAdvertisementScreenContent(
        modifier = modifier,
        uiState = uiState,
        userGroupsList = groupsIdNamePairList,
        onAdImagesChanged = viewModel::onImagesUriChange,
        onAdTitleChanged = viewModel::onTitleChange,
        onAdDescriptionChanged = viewModel::onDescriptionChange,
        onAdPriceChanged = viewModel::onPriceChange,
        onUserGroupSelected = viewModel::onAdGroupSelected,
        onPostAdvertisementClick = {
            viewModel.postNewAdvertisement()
            onPostAdvertisementClick()
        }
    )
}
@Composable
fun NewAdvertisementScreenContent(
    modifier: Modifier = Modifier,
    uiState: NewAdvertisementUiState,
    userGroupsList: List<Pair<String, String>>,
    onAdImagesChanged: (List<Uri>) -> Unit,
    onAdTitleChanged: (String) -> Unit,
    onAdDescriptionChanged: (String) -> Unit,
    onAdPriceChanged: (String) -> Unit,
    onUserGroupSelected: (String) -> Unit,
    onPostAdvertisementClick: () -> Unit
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
            onNewValueTitle = onAdTitleChanged)
        AdDescriptionCard(
            description = uiState.description,
            onAdDescriptionChanged = onAdDescriptionChanged)
        AdPriceCard(
            price = uiState.price,
            onAdPriceChanged = onAdPriceChanged)
        SelectGroupDropdownMenu(
            userGroupsIdNamePairList = userGroupsList,
            onUserGroupSelected = onUserGroupSelected)
        CommonButton(
            onClick = onPostAdvertisementClick,
            text = "Post advertisement")
    }
}

@Composable
fun AdPriceCard(
    modifier: Modifier = Modifier,
    price: String,
    onAdPriceChanged: (String) -> Unit
) {
    ValidatedTextFieldOnCard(
        modifier = modifier,
        value = price,
        onNewValue = onAdPriceChanged,
        label = "Price",
        placeholder = "Enter item price",
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next)
    )
}

@Composable
fun AdDescriptionCard(
    modifier: Modifier = Modifier,
    description: String,
    onAdDescriptionChanged: (String) -> Unit
) {
    ValidatedTextFieldOnCard(
        modifier = modifier.sizeIn(minHeight = 200.dp),
        value = description,
        onNewValue = onAdDescriptionChanged,
        label = "Description",
        placeholder = "Enter item description",
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Default))
}

@Composable
fun AdTitleCard(
    modifier: Modifier = Modifier,
    title: String,
    onNewValueTitle: (String) -> Unit
) {
    ElevatedCard(modifier.cardContentPadding()) {
        TextField(
            value = title,
            onValueChange = onNewValueTitle,
            modifier = modifier.fillMaxWidth(),
            label = { Text(text = "Title")},
            placeholder = { Text(text = "Enter item title")}
        )
    }
}

@Composable
fun AdImagePicker(
    onImagesSelected: (List<Uri>) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedImageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val multiplePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 6)
    ) {
        selectedImageUris = it
        onImagesSelected(selectedImageUris)
    }

    ElevatedCard(modifier.cardContentPadding()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model =
                if (selectedImageUris.isNotEmpty()) {
                    ImageRequest.Builder(LocalContext.current)
                        .data(selectedImageUris[0])
                        .build()
                } else {
                     ImageRequest.Builder(LocalContext.current)
                         .data(R.drawable.placeholder_image)
                         .build()
                       },
                contentDescription = null,
                modifier = modifier
                    .padding(8.dp)
                    .clickable {
                        multiplePhotoPicker.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts
                                    .PickVisualMedia
                                    .ImageOnly
                            )
                        )
                    },
                placeholder = painterResource(R.drawable.placeholder_image)
            )
            HorizontalDivider()
            LazyRow(
                modifier
                    .wrapContentSize()
                    .sizeIn(maxHeight = 70.dp),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(selectedImageUris) {item ->
                    AsyncImage(model = item, contentDescription = null)
                    Spacer(modifier = modifier.width(4.dp))
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectGroupDropdownMenu(
    modifier: Modifier = Modifier,
    userGroupsIdNamePairList: List<Pair<String, String>>,
    onUserGroupSelected: (String) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var groupName by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { newValue ->
            isExpanded = newValue
        },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = groupName,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            placeholder = {
                Text(text = "Please select group")
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = modifier.fillMaxWidth()
        ) {
            userGroupsIdNamePairList.forEach {
                DropdownMenuItem(
                    text = { Text(text = it.second) },
                    onClick = {
                        groupName = it.second
                        onUserGroupSelected(it.first)
                    },
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    }
}
@Preview
@Composable
fun NewAdScreenPreview() {
    NewAdvertisementScreenContent(
        uiState = NewAdvertisementUiState(),
        userGroupsList = emptyList(),
        onAdPriceChanged = {},
        onAdTitleChanged = {},
        onAdImagesChanged = {},
        onAdDescriptionChanged = {},
        onUserGroupSelected = {},
        onPostAdvertisementClick = {},
    )
}
@Preview
@Composable
fun ImagePreviewCard() {
    AdImagePicker({})
}