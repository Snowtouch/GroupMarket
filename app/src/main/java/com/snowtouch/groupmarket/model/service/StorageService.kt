package com.snowtouch.groupmarket.model.service

import android.net.Uri
import com.snowtouch.groupmarket.model.StorageUploadState

interface StorageService {

    suspend fun uploadAdImage(image: Uri, newAdUid: String, currentImageIndex: Int, totalImagesCount: Int) : StorageUploadState

    suspend fun getAllImageUrls(adUid: String): List<String>
}