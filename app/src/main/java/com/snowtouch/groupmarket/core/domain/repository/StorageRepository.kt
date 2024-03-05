package com.snowtouch.groupmarket.core.domain.repository

import android.net.Uri
import com.snowtouch.groupmarket.model.StorageUploadState

interface StorageRepository {

    suspend fun uploadAdImage(image: Uri, newAdUid: String, currentImageIndex: Int, totalImagesCount: Int) : StorageUploadState

    suspend fun getAllImageUrls(adUid: String): List<String>
}