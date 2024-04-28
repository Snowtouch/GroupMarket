package com.snowtouch.feature_new_advertisement.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ServerValue
import com.google.firebase.database.getValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.component1
import com.google.firebase.storage.component2
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.repository.DatabaseReferenceManager
import com.snowtouch.feature_new_advertisement.domain.model.UploadStatus
import com.snowtouch.feature_new_advertisement.domain.repository.NewAdRemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneOffset

class NewAdRemoteRepositoryImpl(
    private val auth : FirebaseAuth,
    private val dbReferences : DatabaseReferenceManager,
    storage : FirebaseStorage,
    private val dispatcher : CoroutineDispatcher,
) : NewAdRemoteRepository {

    private val storageReference = storage.reference

    override suspend fun getUserGroupsIdNamePairs() : Result<List<Map<String, String>>> {
        return withContext(dispatcher) {
            try {
                val userGroupsDataSnap = dbReferences.currentUserGroupsIdNamesPairs
                    .get()
                    .await()
                val userGroupsData = userGroupsDataSnap.children.mapNotNull {
                    it.getValue<Map<String, String>>()
                }
                Result.Success(userGroupsData)
            } catch (e : Exception) {
                Result.Failure(e)
            }
        }
    }

    override suspend fun getNewAdId() : String? {
        return withContext(dispatcher) {
            try {
                val newAdKey = dbReferences.advertisements.push().key
                newAdKey
            } catch (e : Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    override suspend fun postNewAdvertisement(advertisement : Advertisement) : Result<Boolean> {
        return try {
            val newAdKey = advertisement.uid
            val adWithOwnerUid = advertisement.copy(
                ownerUid = auth.currentUser?.uid,
                postDateTimestamp = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()
            )
            if (newAdKey != null) {
                dbReferences.advertisements.child(newAdKey)
                    .setValue(adWithOwnerUid).await()
                dbReferences.advertisementsPreview.child(newAdKey)
                    .setValue(adWithOwnerUid.toAdvertisementPreview()).await()
                dbReferences.groupAdsIdList.child(adWithOwnerUid.groupId!!).push()
                    .setValue(newAdKey).await()
                dbReferences.groups.child(adWithOwnerUid.groupId!!)
                    .child("advertisementsCount")
                    .setValue(ServerValue.increment(1)).await()
                dbReferences.groupsPreview.child(adWithOwnerUid.groupId!!)
                    .child("advertisementsCount")
                    .setValue(ServerValue.increment(1)).await()
                dbReferences.currentUserActiveAdsIds.push()
                    .setValue(newAdKey).await()
                Result.Success(true)
            } else {
                Result.Failure(Exception("Unknown error"))
            }
        } catch (e : Exception) {
            Result.Failure(e)
        }
    }

    override fun uploadAdImage(adId : String, imageName : String, imageBytes : ByteArray)
            : Flow<UploadStatus> = callbackFlow {
        withContext(dispatcher) {
            var progress : Long
            storageReference.child("$adId/$imageName.jpg")
                .putBytes(imageBytes)

                .addOnProgressListener { (bytesTransferred, totalByteCount) ->
                    progress = (100 * bytesTransferred / totalByteCount)
                    Log.d("Storage upload progress: ", "$progress")
                    trySend(UploadStatus.Progress(progress))
                }

                .addOnFailureListener { exception ->
                    Log.d("Storage upload error: ", "$exception")
                    trySend(UploadStatus.Failure(exception))
                }

                .addOnSuccessListener {  task ->
                    val downloadUrlTask = task.storage.downloadUrl
                    if (!downloadUrlTask.isSuccessful) {
                        trySend(UploadStatus.Failure(downloadUrlTask.exception ?: Exception("Error")))
                    }
                    downloadUrlTask
                        .addOnSuccessListener {
                            trySend(UploadStatus.Success(it))
                            Log.d("Storage upload url: ", "$it")
                            close()
                        }
                }
        }
        awaitClose()
    }
}