package com.snowtouch.core.domain.repository

import android.net.Uri

interface StorageRepository {

    suspend fun uploadAdImage(image: Uri, newAdUid: String, currentImageIndex: Int, totalImagesCount: Int)

    suspend fun getAllImageUrls(adUid: String): List<String>
}