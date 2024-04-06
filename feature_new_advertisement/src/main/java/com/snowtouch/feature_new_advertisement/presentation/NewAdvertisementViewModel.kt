package com.snowtouch.feature_new_advertisement.presentation

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.presentation.GroupMarketViewModel
import com.snowtouch.feature_new_advertisement.domain.model.UploadStatus
import com.snowtouch.feature_new_advertisement.domain.model.UploadStatus.Failure
import com.snowtouch.feature_new_advertisement.domain.model.UploadStatus.Progress
import com.snowtouch.feature_new_advertisement.domain.model.UploadStatus.Success
import com.snowtouch.feature_new_advertisement.domain.repository.NewAdLocalRepository
import com.snowtouch.feature_new_advertisement.domain.repository.NewAdRemoteRepository
import com.snowtouch.feature_new_advertisement.presentation.new_advertisement.NewAdUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale
import java.util.UUID

internal class NewAdvertisementViewModel(
    private val remoteRepository : NewAdRemoteRepository,
    private val localRepository : NewAdLocalRepository,
) : GroupMarketViewModel() {

    private val _uiState = MutableStateFlow<NewAdUiState>(NewAdUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getUserGroupsIdNamePairs()
    }

    fun onTitleChange(newValue : String) {
        if (newValue.length <= 100)
            _uiState.update {
                NewAdUiState.EditingNewAd(
                    title = newValue,
                    titleFieldError = newValue.isBlank()
                )
            }
        else return
    }

    fun onDescriptionChange(newValue : String) {
        if (newValue.length <= 500)
            _uiState.update {
                NewAdUiState.EditingNewAd(
                    description = newValue,
                    descriptionFiledError = newValue.isBlank()
                )
            }
        else return
    }

    fun onPriceChange(newValue : String) {
        _uiState.update {
            NewAdUiState.EditingNewAd(
                price = validatePrice(newValue),
                priceFieldError = newValue.isBlank()
            )
        }
    }

    fun onImagesUriChange(newValue : List<Uri>) {
        _uiState.update { NewAdUiState.EditingNewAd(images = newValue) }
    }

    fun onUserGroupSelected(newValue : String) {
        _uiState.update {
            NewAdUiState.EditingNewAd(
                selectedGroupId = newValue, groupFieldError = newValue.isBlank()
            )
        }
    }

    fun getUserGroupsIdNamePairs() {
        launchCatching {
            _uiState.update { NewAdUiState.Loading }
            _uiState.update {
                when (val response = remoteRepository.getUserGroupsIdNamePairs()) {
                    is Result.Failure -> NewAdUiState.Error(response.e)
                    is Result.Loading -> NewAdUiState.Loading
                    is Result.Success -> NewAdUiState.EditingNewAd(
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

    private fun uploadNewAdImages(
        context : Context,
        adId : String,
        imagesUriList : List<Uri>,
    ) : List<String> {
        _uiState.value = NewAdUiState.Uploading(0, 1, imagesUriList.size)
        val imagesDownloadUrlList = mutableListOf<String>()

        launchCatching {
            imagesUriList.mapIndexed { index, uri ->
                val inputStream = context.contentResolver.openInputStream(uri)
                val byteArray = inputStream?.readBytes()

                if (byteArray != null) {
                    val randomFileName = UUID.randomUUID().toString()
                    remoteRepository.uploadAdImage(adId, randomFileName, byteArray)
                        .collect { response ->
                            imagesDownloadUrlList.add(handleUploadResponse(response, index))
                        }
                } else {
                    _uiState.value = NewAdUiState.Error(Exception("Failed to upload image"))
                }
                inputStream?.close()
            }
        }
        return imagesDownloadUrlList
    }

    fun postNewAdvertisement(context : Context) {
        if (!validateAdvertisement())
            return

        launchCatching {
            _uiState.update { NewAdUiState.Loading }

            val newAdKey = remoteRepository.getNewAdId()
            if (newAdKey.isNullOrBlank()) {
                _uiState.update { NewAdUiState.Error(Exception("Failed to post advertisement")) }
                return@launchCatching
            }

            _uiState.update { NewAdUiState.EditingNewAd(newAdId = newAdKey) }

            val currentState = _uiState.value as NewAdUiState.EditingNewAd
            val newAdImagesStorageUrl = uploadNewAdImages(context, newAdKey, currentState.images)


            if (newAdImagesStorageUrl.size == currentState.images.size) {
                _uiState.update {
                    when (val response = remoteRepository.postNewAdvertisement(
                        Advertisement(
                            uid = newAdKey,
                            groupId = currentState.selectedGroupId,
                            title = currentState.title,
                            images = newAdImagesStorageUrl,
                            description = currentState.description,
                            price = currentState.price))
                    ) {
                        is Result.Failure -> NewAdUiState.Error(response.e)
                        is Result.Loading -> NewAdUiState.Loading
                        is Result.Success -> NewAdUiState.Success(response.data ?: "")
                    }
                }
            } else {
                _uiState.update { NewAdUiState.Error(Exception("Uploading images failed")) }
            }
        }
    }

    private fun handleUploadResponse(response : UploadStatus, index : Int) : String {
        val currentUiState = _uiState.value as NewAdUiState.Uploading
        var imageDownloadUri = ""
        when (response) {
            is Progress -> _uiState.update {
                NewAdUiState.Uploading(
                    progress = response.progress,
                    currentImageIndex = index + 1,
                    imageCount = currentUiState.imageCount
                )
            }

            is Success -> imageDownloadUri = response.uri.toString()
            is Failure -> _uiState.update { NewAdUiState.Error(response.e) }
        }
        return imageDownloadUri
    }

    private fun validateAdvertisement() : Boolean {
        val state = _uiState.value as NewAdUiState.EditingNewAd
        return state.titleFieldError ||
                state.descriptionFiledError ||
                state.priceFieldError ||
                state.groupFieldError
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