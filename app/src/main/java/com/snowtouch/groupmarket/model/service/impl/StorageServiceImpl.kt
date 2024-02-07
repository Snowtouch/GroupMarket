package com.snowtouch.groupmarket.model.service.impl

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.snowtouch.groupmarket.model.service.StorageService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class StorageServiceImpl(
    private val firebaseStorage: FirebaseStorage,
    private val dispatcher: CoroutineDispatcher
) : StorageService {

    private val adImageRef = firebaseStorage.reference.child("ads")

    override suspend fun uploadAdImages(images: List<Uri>, adDbReferenceKey: String): List<Uri> {
        return withContext(dispatcher) {
            val imagesDownloadRef = mutableListOf<Uri>()

            val tasks = mutableListOf<Task<Uri>>()

            images.forEach { image ->
                val imageRef = adImageRef
                    .child("${adDbReferenceKey}/${image.lastPathSegment}")

                val uploadTask = imageRef.putFile(image)

                tasks.add(uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let { throw it }
                    }
                    imageRef.downloadUrl
                })
            }
            // Waiting for all tasks to complete
            Tasks.whenAllComplete(tasks)
                .addOnCompleteListener {
                    // When all complete, get results
                    if (it.isSuccessful) {
                        tasks.forEach { task ->
                            if (task.isSuccessful) {
                                imagesDownloadRef.add(task.result)
                            }
                        }
                    }
                }
                .await()
            return@withContext imagesDownloadRef
        }
    }
    override suspend fun getImageDownloadLink(dbRef: StorageReference): Uri {
        TODO("Not yet implemented")
    }
}