package com.snowtouch.feature_new_advertisement.presentation.new_advertisement

import android.net.Uri

internal sealed interface NewAdUiState {
    data class Uploading(
        val progress : Long,
        val currentImageIndex : Int,
        val imageCount : Int,
    ) : NewAdUiState

    data object Loading : NewAdUiState
    data class Success(val newAdId : String) : NewAdUiState
    data class Error(val e : Exception) : NewAdUiState
    data class EditingNewAd(
        val newAdId : String = "",
        val title : String = "",
        val titleFieldError : Boolean = false,
        val description : String = "",
        val descriptionFiledError : Boolean = false,
        val price : String = "",
        val priceFieldError : Boolean = false,
        val groupIdNames : List<Map<String, String>> = emptyList(),
        val selectedGroupId : String = "",
        val groupFieldError : Boolean = false,
        val images : List<Uri> = emptyList(),
    ) : NewAdUiState
}