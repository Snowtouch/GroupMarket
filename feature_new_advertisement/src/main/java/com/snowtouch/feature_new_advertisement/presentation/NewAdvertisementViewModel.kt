package com.snowtouch.feature_new_advertisement.presentation

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Response
import com.snowtouch.core.presentation.GroupMarketViewModel
import com.snowtouch.core.presentation.util.SnackbarState
import com.snowtouch.feature_new_advertisement.domain.model.StorageUploadState
import com.snowtouch.feature_new_advertisement.domain.repository.NewAdLocalRepository
import com.snowtouch.feature_new_advertisement.domain.repository.NewAdRemoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

class NewAdvertisementViewModel(
    private val remoteRepository : NewAdRemoteRepository,
    private val localRepository : NewAdLocalRepository,
): GroupMarketViewModel() {

    private val _newAdPostResponse = MutableStateFlow<Response<Boolean>>(Response.Success(false))
    val newAdPostResponse: StateFlow<Response<Boolean>> = _newAdPostResponse

    private val _uploadState = MutableStateFlow<StorageUploadState>(
        StorageUploadState.InProgress(
            progress = 0.0,
            currentImageIndex = 0,
            totalImagesCount = 0)
    )
    val uploadState: StateFlow<StorageUploadState> = _uploadState

    var uiState = mutableStateOf(NewAdvertisementUiState())
        private set


    fun onTitleChange(newValue: String) {
        uiState.value = uiState.value.copy(title = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        uiState.value = uiState.value.copy(description = newValue)
    }

    fun onPriceChange(newValue: String) {
        uiState.value = uiState.value.copy(price = getValidatedPrice(newValue))
    }
    fun onImagesUriChange(newValue: List<Uri>) {
        uiState.value = uiState.value.copy(images = newValue)
    }

    fun onAdGroupSelected(newValue: String) {
        uiState.value = uiState.value.copy(groupId = newValue)
    }

    private fun getValidatedPrice(price: String): String {
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

    private fun uploadAdImages(adUid: String) {
        launchCatching {
            val images = uiState.value.images

            images.forEachIndexed { index, image ->
                val result = storageRepository.uploadAdImage(image, adUid, index + 1, images.size)
                _uploadState.value = result

                if (result is StorageUploadState.Error) {
                    showSnackbar(SnackbarState.ERROR, result.errorMessage)
                    return@launchCatching
                }
            }
        }
    }

    private fun getAdImagesUrls(adUid: String) : List<String> {
        var uriList = mutableListOf<String>()
        launchCatching {
             uriList = storageRepository.getAllImageUrls(adUid).toMutableList()
        }
    return uriList
    }

    fun postNewAdvertisement() {
        launchCatching {
            if (validateNewAd()) {
                val newAdUid = databaseRepository.getNewAdReferenceKey() ?: ""
                uploadAdImages(newAdUid)
                val adImagesStorageRef = getAdImagesUrls(newAdUid)


                val adWithImages = Advertisement(
                    uid = newAdUid,
                    groupId = uiState.value.groupId,
                    title = uiState.value.title,
                    images = adImagesStorageRef,
                    description = uiState.value.description,
                    price = uiState.value.price.toDouble(),
                    postDate = Date()//LocalDateTime.now()
                )
                databaseRepository.createAdvertisement(adWithImages, newAdUid)
            }
            else {
                showSnackbar(SnackbarState.ERROR, "Please fill in all fields.")
                return@launchCatching
            }
        }
    }

    private fun validateNewAd(): Boolean {
        return uiState.value.images.isNotEmpty() &&
                uiState.value.groupId.isNotBlank() &&
                uiState.value.title.isNotBlank() &&
                uiState.value.description.isNotEmpty() &&
                uiState.value.price.isNotEmpty()
    }
}