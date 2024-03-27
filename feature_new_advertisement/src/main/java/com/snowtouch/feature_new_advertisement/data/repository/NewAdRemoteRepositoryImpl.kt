package com.snowtouch.feature_new_advertisement.data.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ServerValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.component1
import com.google.firebase.storage.component2
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Response
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

    private val adImagesRef = storage.getReference("ad_images")

    override suspend fun getNewAdId() : String? {
        return withContext(dispatcher) {
            try {
                val newAdKey = dbReferences.advertisements.push().key
                newAdKey
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    override suspend fun postNewAdvertisement(advertisement : Advertisement) : Response<Boolean> {
        return withContext(dispatcher) {
            try {
                val newAdKey = advertisement.uid
                val adWithOwnerUid = advertisement.copy(
                    ownerUid = auth.currentUser?.uid,
                    postDateTimestamp = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()
                )

                if (newAdKey != null) {
                    dbReferences.advertisements.child(newAdKey).setValue(adWithOwnerUid).await()
                    dbReferences.advertisementsPreview.child(newAdKey)
                        .setValue(adWithOwnerUid.toAdvertisementPreview()).await()

                    dbReferences.groupAdsIdList.child(adWithOwnerUid.groupId!!).push()
                        .setValue(newAdKey).await()
                    dbReferences.groupAdsCounter.child(adWithOwnerUid.groupId!!)
                        .setValue(ServerValue.increment(1))

                    dbReferences.currentUserActiveAdsIds.push().setValue(newAdKey).await()
                    Response.Success(true)
                } else {
                    Response.Failure(Exception("Unknown error"))
                }
            } catch (e : Exception) {
                Response.Failure(e)
            }
        }
    }

    override fun uploadAdImages(adId : String, images : List<Uri>)
    : Flow<UploadStatus> = callbackFlow {
        withContext(dispatcher) {
            images.forEach { image ->
                var progress : Long

                val downloadUrl = adImagesRef.child(adId)
                    .putFile(image)
                    .addOnProgressListener { (bytesTransferred, totalByteCount) ->
                        progress = (100 * bytesTransferred / totalByteCount)
                        trySend(UploadStatus.Progress(progress))
                    }
                    .addOnFailureListener {
                        trySend(UploadStatus.Failure(it))
                    }
                    .await()
                    .storage.downloadUrl.await()
                trySend(UploadStatus.Success(downloadUrl))
            }
            awaitClose()
        }
    }
}

fun Advertisement.toAdvertisementPreview() : AdvertisementPreview {
    return AdvertisementPreview(
        uid = this.uid,
        groupId = this.groupId,
        title = this.title,
        image = this.images?.get(0),
        price = this.price
    )
}