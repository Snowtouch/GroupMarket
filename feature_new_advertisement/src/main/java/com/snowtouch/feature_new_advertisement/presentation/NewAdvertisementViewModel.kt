package com.snowtouch.feature_new_advertisement.presentation

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.lifecycle.viewModelScope
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.presentation.GroupMarketViewModel
import com.snowtouch.feature_new_advertisement.domain.model.UploadStatus
import com.snowtouch.feature_new_advertisement.domain.model.UploadStatus.Failure
import com.snowtouch.feature_new_advertisement.domain.model.UploadStatus.Progress
import com.snowtouch.feature_new_advertisement.domain.model.UploadStatus.Success
import com.snowtouch.feature_new_advertisement.domain.repository.NewAdLocalRepository
import com.snowtouch.feature_new_advertisement.domain.repository.NewAdRemoteRepository
import com.snowtouch.feature_new_advertisement.presentation.new_advertisement.ScreenState
import com.snowtouch.feature_new_advertisement.presentation.new_advertisement.UiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID

internal class NewAdvertisementViewModel(
    private val remoteRepository : NewAdRemoteRepository,
    private val localRepository : NewAdLocalRepository,
) : GroupMarketViewModel() {

    var uiState = MutableStateFlow(UiState())
        private set

    private var job : Job? = null

    fun onTitleChange(newValue : String) {
        if (newValue.length <= 100)
            uiState.update { it.copy(title = newValue, titleFieldError = newValue.isBlank()) }
        else return
    }

    fun onDescriptionChange(newValue : String) {
        if (newValue.length <= 500)
            uiState.update {
                it.copy(description = newValue, descriptionFiledError = newValue.isBlank())
            }
        else return
    }

    fun onPriceChange(newValue : String) {
        uiState.update {
            it.copy(price = validatePrice(newValue), priceFieldError = newValue.isBlank())
        }
    }

    fun onImagesUriChange(newValue : List<Uri>) {
        uiState.update {
            it.copy(images = newValue)
        }
    }

    fun onUserGroupSelected(newValue : String) {
        uiState.update {
            it.copy(selectedGroupId = newValue, groupFieldError = newValue.isBlank())
        }
    }

    fun getUserGroupsIdNamePairs() {
        launchCatching {
            uiState.update {
                it.copy(screenState = ScreenState.Loading)
            }
            uiState.update {
                when (val response = remoteRepository.getUserGroupsIdNamePairs()) {
                    is Result.Failure -> it.copy(screenState = ScreenState.Error(response.e))
                    is Result.Loading -> it.copy(screenState = ScreenState.Loading)
                    is Result.Success -> it.copy(
                        screenState = ScreenState.Success,
                        groupIdNames = response.data ?: emptyList()
                    )
                }
            }
        }
    }

    private fun getFileExtension(context : Context, uri : Uri) : String? {
        val fileExtension : String? =
            when (uri.scheme) {
                ContentResolver.SCHEME_CONTENT -> context.contentResolver.getType(uri)
                ContentResolver.SCHEME_FILE -> MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    MimeTypeMap.getFileExtensionFromUrl(uri.toString())
                        .lowercase(Locale.getDefault())
                )

                else -> null
            }
        Log.d("Image file ext ", "ext: $fileExtension")
        return fileExtension
    }

    fun postAdvertisement(context : Context) {
        viewModelScope.launch {
            val adId = getNewAdId()
            if (adId != null) {
                val urlList =
                    uploadNewAdImages(context, adId, uiState.value.images)
                saveNewAdDataInDb(adId, urlList)
            } else {
                uiState.update { it.copy(screenState = ScreenState.Error(Exception("Failed to post"))) }
            }
        }
    }

    private suspend fun getNewAdId(): String? {
        val adIdDeferred = viewModelScope.async {
            remoteRepository.getNewAdId()
        }
        return adIdDeferred.await()
    }

    private suspend fun uploadNewAdImages(
        context: Context,
        adId: String,
        imagesUriList: List<Uri>,
    ): List<String> {
        return viewModelScope.async {
            val imagesDownloadUrlList = mutableListOf<String>()
            uiState.update {
                it.copy(screenState = ScreenState.Uploading(0, 1, imagesUriList.size))
            }

            imagesUriList.forEachIndexed { index, uri ->
                val inputStream = context.contentResolver.openInputStream(uri)
                val byteArray = inputStream?.readBytes()

                if (byteArray != null) {
                    val randomFileName = UUID.randomUUID().toString()
                    remoteRepository.uploadAdImage(adId, randomFileName, byteArray)
                        .collect { response ->
                            val url = handleUploadResponse(response, index)
                            if (url != null) imagesDownloadUrlList.add(url)
                            Log.d("upload images", "list $imagesDownloadUrlList")
                        }
                } else {
                    uiState.update {
                        it.copy(screenState = ScreenState.Error(Exception("Failed to upload Images")))
                    }
                    return@forEachIndexed
                }
                inputStream.close()
            }
            imagesDownloadUrlList
        }.await()
    }

    private fun saveNewAdDataInDb(newAdId : String, imagesUrlList : List<String>) {
        viewModelScope.launch {
            if (validateAdvertisement())
                return@launch
            uiState.update { it.copy(screenState = ScreenState.Loading, newAdId = newAdId) }

            if (imagesUrlList.size == uiState.value.images.size) {
                uiState.update {
                    when (val response = remoteRepository.postNewAdvertisement(
                        Advertisement(
                            uid = newAdId,
                            groupId = uiState.value.selectedGroupId,
                            title = uiState.value.title,
                            images = imagesUrlList,
                            description = uiState.value.description,
                            price = uiState.value.price
                        )
                    )
                    ) {
                        is Result.Failure -> it.copy(screenState = ScreenState.Error(response.e))
                        is Result.Loading -> it.copy(screenState = ScreenState.Loading)
                        is Result.Success -> it.copy(screenState = ScreenState.AdPostSuccess)
                    }
                }
            } else {
                uiState.update { it.copy(screenState = ScreenState.Error(Exception("Uploading images failed"))) }
            }
        }
    }

    private fun handleUploadResponse(response : UploadStatus, index : Int) : String? {
        var imageDownloadUri : String? = null
        when (response) {
            is Progress -> uiState.update {
                it.copy(
                    screenState = ScreenState.Uploading(
                        progress = response.progress,
                        currentImageIndex = index + 1,
                        imageCount = uiState.value.images.size
                    )
                )

            }

            is Success -> imageDownloadUri = response.uri.toString()
            is Failure -> uiState.update { it.copy(screenState = ScreenState.Error(response.e)) }
        }
        return imageDownloadUri
    }

    private fun validateAdvertisement() : Boolean {
        return uiState.value.titleFieldError ||
                uiState.value.descriptionFiledError ||
                uiState.value.priceFieldError ||
                uiState.value.groupFieldError
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