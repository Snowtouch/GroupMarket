package com.snowtouch.feature_new_advertisement.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.core.domain.model.Response
import com.snowtouch.core.presentation.components.Loading
import com.snowtouch.feature_new_advertisement.presentation.NewAdvertisementViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NewAdvertisement(
    modifier : Modifier = Modifier,
    viewModel : NewAdvertisementViewModel = koinViewModel(),
) {
    val newAdPostDataResponse by viewModel.newAdPostResponse.collectAsStateWithLifecycle()

    when (val newAdPostResponse = newAdPostDataResponse) {
        is Response.Loading -> Loading(modifier = modifier)
        is Response.Success ->
    }
}