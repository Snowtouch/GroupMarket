package com.snowtouch.groupmarket.model.service

import android.net.Uri
import com.google.firebase.storage.StorageReference

interface StorageService {
    suspend fun uploadAdImages(images: List<Uri>, adDbReferenceKey: String): List<Uri>
    suspend fun getImageDownloadLink(dbRef: StorageReference): Uri
}