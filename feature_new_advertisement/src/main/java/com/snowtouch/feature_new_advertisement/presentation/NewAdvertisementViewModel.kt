package com.snowtouch.feature_new_advertisement.presentation

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Response
import com.snowtouch.core.presentation.GroupMarketViewModel
import com.snowtouch.feature_new_advertisement.domain.model.UploadStatus
import com.snowtouch.feature_new_advertisement.domain.repository.NewAdLocalRepository
import com.snowtouch.feature_new_advertisement.domain.repository.NewAdRemoteRepository
import com.snowtouch.feature_new_advertisement.presentation.new_advertisement.NewAdvertisementUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NewAdvertisementViewModel(
    private val remoteRepository : NewAdRemoteRepository,
    private val localRepository : NewAdLocalRepository,
) : GroupMarketViewModel() {

    private val _postAdvertisementResponse =
        MutableStateFlow<Response<Boolean>>(Response.Loading(null))
    val postAdvertisementResponse : StateFlow<Response<Boolean>> = _postAdvertisementResponse

    var uiState = mutableStateOf(NewAdvertisementUiState())
        private set

    private val _uploadUiState =
        MutableStateFlow<ImageUploadUiState>(
            ImageUploadUiState.Uploading(0, 0, 0)
        )
    val uploadUiState : StateFlow<ImageUploadUiState> = _uploadUiState

    fun onTitleChange(newValue : String) {
        uiState.value = uiState.value.copy(title = newValue)
    }

    fun onDescriptionChange(newValue : String) {
        uiState.value = uiState.value.copy(description = newValue)
    }

    fun onPriceChange(newValue : String) {
        uiState.value = uiState.value.copy(price = validatePrice(newValue))
    }

    fun onImagesUriChange(newValue : List<Uri>) {
        uiState.value = uiState.value.copy(images = newValue)
    }

    fun onAdGroupSelected(newValue : String) {
        uiState.value = uiState.value.copy(groupId = newValue)
    }


    fun postNewAdvertisement() {
        if (!validateNewAd())
            return

        launchCatching {
            _postAdvertisementResponse.value = Response.Loading(null)
            _uploadUiState.value = ImageUploadUiState.Uploading(0, 0, uiState.value.images.size)

            val newAdKey = remoteRepository.getNewAdId()
            val imagesUrlList = mutableListOf<String>()

            if (newAdKey.isNullOrBlank()) {
                return@launchCatching
            }

            uiState.value = uiState.value.copy(newAdId = newAdKey)

            remoteRepository.uploadAdImages(newAdKey, uiState.value.images)
                .collect { response ->
                    when (response) {
                        is UploadStatus.Progress -> {
                            _uploadUiState.value = ImageUploadUiState.Uploading(
                                response.progress,
                                imagesUrlList.size + 1,
                                uiState.value.images.size
                            )
                        }

                        is UploadStatus.Success -> {
                            imagesUrlList.add(response.uri.toString())
                        }

                        is UploadStatus.Failure -> {
                            _uploadUiState.value =
                                ImageUploadUiState.Error(response.e.localizedMessage ?: "Error")
                        }
                    }
                }

            if (imagesUrlList.size == uiState.value.images.size) {
                _postAdvertisementResponse.value = Response.Loading(null)
                _postAdvertisementResponse.value = remoteRepository.postNewAdvertisement(
                    Advertisement(
                        uid = newAdKey,
                        groupId = uiState.value.groupId,
                        title = uiState.value.title,
                        images = imagesUrlList,
                        description = uiState.value.description,
                        price = uiState.value.price
                    )
                )
            }
        }
    }

    private fun validateNewAd() : Boolean {
        return uiState.value.images.isNotEmpty() &&
                uiState.value.groupId.isNotBlank() &&
                uiState.value.title.isNotBlank() &&
                uiState.value.description.isNotEmpty() &&
                uiState.value.price.isNotEmpty()
    }

    private fun validatePrice(price : String) : String {
        val filteredChars = price.filterIndexed { index, char ->
            char.isDigit()
                    || (char == '.' && index != 0 && price.indexOf('.') == index)
                    || (char == '.' && index != 0 && price.count { it == '.' } <= 1)
        }
        val trimmedPrice = filteredChars.removePrefix("0")
        if (trimmedPrice.isBlank()) {
            return ""
        }
        return if (trimmedPrice.count { it == '.' } == 1) {
            val beforeDecimal = trimmedPrice.substringBefore('.')
            val afterDecimal = trimmedPrice.substringAfter('.')
            beforeDecimal + "." + afterDecimal.take(2)
        } else {
            trimmedPrice
        }
    }
}

sealed interface ImageUploadUiState {
    data object Success : ImageUploadUiState
    data class Error(val error : String) : ImageUploadUiState
    data class Uploading(
        val progress : Long?,
        val currentImageIndex : Int,
        val numberOfImages : Int,
    ) : ImageUploadUiState
}