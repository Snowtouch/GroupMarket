package com.snowtouch.groupmarket.screens.new_advertisement

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import com.snowtouch.groupmarket.common.snackbar.SnackbarState
import com.snowtouch.groupmarket.model.Advertisement
import com.snowtouch.groupmarket.model.Group
import com.snowtouch.groupmarket.model.StorageUploadState
import com.snowtouch.groupmarket.model.service.DatabaseService
import com.snowtouch.groupmarket.model.service.StorageService
import com.snowtouch.groupmarket.screens.GroupMarketViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime

class NewAdvertisementScreenViewModel(
    private val storageService: StorageService,
    private val databaseService: DatabaseService
): GroupMarketViewModel() {

    private val _userGroupsData: StateFlow<List<Group?>> = databaseService.userGroupsData

    private val _groupsIdNamePairList = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val groupsIdNamePairList: StateFlow<List<Pair<String, String>>> = _groupsIdNamePairList

    private val _uploadState = MutableStateFlow<StorageUploadState>(
        StorageUploadState.UploadInProgress(
            progress = 0.0,
            currentImageIndex = 0,
            totalImagesCount = 0)
    )
    val uploadState: StateFlow<StorageUploadState> = _uploadState

    var uiState = mutableStateOf(NewAdvertisementUiState())
        private set


    init {
        launchCatching {
            _userGroupsData.collect { groups ->
                val pairs = groups.map { group ->
                    Pair(group?.uid ?: "", group?.name ?: "")
                }
                _groupsIdNamePairList.value = pairs
            }
        }
    }

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
                val result = storageService.uploadAdImage(image, adUid, index + 1, images.size)
                _uploadState.value = result

                if (result is StorageUploadState.UploadError) {
                    showSnackbar(SnackbarState.ERROR, result.errorMessage)
                    return@launchCatching
                }
            }
        }
    }

    private fun getAdImagesUrls(adUid: String) : List<String> {
        var uriList = mutableListOf<String>()
        launchCatching {
             uriList = storageService.getAllImageUrls(adUid).toMutableList()
        }
    return uriList
    }

    fun postNewAdvertisement() {
        launchCatching {
            if (validateNewAd()) {
                val newAdUid = databaseService.getNewAdReferenceKey() ?: ""
                uploadAdImages(newAdUid)
                val adImagesStorageRef = getAdImagesUrls(newAdUid)


                val adWithImages = Advertisement(
                    uid = newAdUid,
                    groupId = uiState.value.groupId,
                    title = uiState.value.title,
                    images = adImagesStorageRef,
                    description = uiState.value.description,
                    price = uiState.value.price,
                    postDate = LocalDateTime.now().toString()
                )
                databaseService.createAdvertisement(adWithImages, newAdUid)
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