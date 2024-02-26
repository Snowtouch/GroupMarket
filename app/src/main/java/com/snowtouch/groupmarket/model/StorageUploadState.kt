package com.snowtouch.groupmarket.model

sealed class StorageUploadState {
    data class UploadInProgress(val progress: Double, val currentImageIndex: Int, val totalImagesCount: Int) : StorageUploadState()
    data object UploadSuccess : StorageUploadState()
    data class UploadError(val errorMessage: String) : StorageUploadState()
}