package com.snowtouch.feature_new_advertisement.presentation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.snowtouch.core.presentation.components.ext.cardContentPadding
import com.snowtouch.feature_new_advertisement.R

@Composable
fun AdImagePicker(
    onImagesSelected : (List<Uri>) -> Unit,
    modifier : Modifier = Modifier,
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
                                ActivityResultContracts.PickVisualMedia.ImageOnly
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
                items(selectedImageUris) { item ->
                    AsyncImage(model = item, contentDescription = null)
                    Spacer(modifier = modifier.width(4.dp))
                }
            }
        }
    }
}