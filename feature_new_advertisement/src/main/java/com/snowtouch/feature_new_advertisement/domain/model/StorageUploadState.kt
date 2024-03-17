package com.snowtouch.feature_new_advertisement.domain.model

sealed class StorageUploadState {
    data class InProgress(val progress: Double, val currentImageIndex: Int, val totalImagesCount: Int) : StorageUploadState()
    data object Success : StorageUploadState()
    data class Error(val errorMessage: String) : StorageUploadState()
}