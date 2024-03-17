package com.snowtouch.groupmarket.model.service.impl

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.snowtouch.feature_new_advertisement.domain.model.StorageUploadState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class StorageRepositoryImpl(
    firebaseStorage: FirebaseStorage,
    private val dispatcher: CoroutineDispatcher,
) : com.snowtouch.core.domain.repository.StorageRepository {

    private val adImageRef = firebaseStorage.reference.child("ads")

    override suspend fun uploadAdImage(
        image: Uri,
        newAdUid: String,
        currentImageIndex: Int,
        totalImagesCount: Int
    ): StorageUploadState {
        return withContext(dispatcher) {

            val imageRef = adImageRef.child("${newAdUid}/${image.lastPathSegment}")
            val uploadTask = imageRef.putFile(image)

            return@withContext suspendCancellableCoroutine { continuation ->
                uploadTask
                    .addOnProgressListener {
                        val progress = (100.0 * it.bytesTransferred) / it.totalByteCount
                        continuation.resume(StorageUploadState.InProgress(progress, currentImageIndex, totalImagesCount))
                    }

                    .addOnSuccessListener {
                        continuation.resume(StorageUploadState.Success)
                    }

                    .addOnFailureListener { exception ->
                        continuation.resume(StorageUploadState.Error(exception.message ?: "Unknown error"))
                    }
            }
        }
    }

    override suspend fun getAllImageUrls(adUid: String): List<String> {
        return withContext(dispatcher) {
            val imagesRef = adImageRef.child(adUid)
            val result = mutableListOf<String>()
            val items = imagesRef.listAll().await()

            items.items.forEach { imageRef ->
                val url = imageRef.downloadUrl.await().toString()
                result.add(url)
            }
            return@withContext result
        }
    }
}