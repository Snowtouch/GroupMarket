package com.snowtouch.feature_new_advertisement.domain.model

import android.net.Uri

sealed class UploadStatus {

    data class Progress(val progress: Long) : UploadStatus()

    data class Success(val uri: Uri) : UploadStatus()

    data class Failure(val e: Exception) : UploadStatus()
}
