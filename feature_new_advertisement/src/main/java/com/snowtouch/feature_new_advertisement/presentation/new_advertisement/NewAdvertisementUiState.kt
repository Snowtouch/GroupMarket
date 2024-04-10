package com.snowtouch.feature_new_advertisement.presentation.new_advertisement

import android.net.Uri

data class UiState(
    val screenState : ScreenState = ScreenState.Loading,
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
)

sealed interface ScreenState {
    data object Loading : ScreenState
    data class Uploading(
        val progress : Long,
        val currentImageIndex : Int,
        val imageCount : Int,
    ) : ScreenState
    data object AdPostSuccess : ScreenState
    data object Success : ScreenState
    data class Error(val e : Exception) : ScreenState
}