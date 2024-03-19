package com.snowtouch.feature_new_advertisement.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue
import com.google.firebase.storage.FirebaseStorage
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Response
import com.snowtouch.feature_new_advertisement.domain.repository.NewAdRemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class NewAdRemoteRepositoryImpl(
    auth : FirebaseAuth,
    db : FirebaseDatabase,
    private val storage : FirebaseStorage,
    private val dispatcher : CoroutineDispatcher
) : NewAdRemoteRepository {

    private val advertisementsPreviewRef = db.getReference("ads_preview")
    private val advertisementsRef = db.getReference("ads")

    private val groupsAdsIdList = db.getReference("groups_adsId_list")
    private val groupsAdsCounterRef = db.getReference("groups_ads_counter")

    private val currentUserAdsRef = db.getReference("users").child(auth.currentUser?.uid!!)

    override suspend fun postNewAdvertisement(advertisement : Advertisement) : Response<Boolean> {
        return withContext(dispatcher) {
            try {
                val newAdKey = advertisementsRef.push().key
                if (newAdKey != null) {
                    advertisementsRef.child(newAdKey).setValue(advertisement).await()
                    advertisementsPreviewRef.child(newAdKey)
                        .setValue(advertisement.toAdvertisementPreview()).await()

                    groupsAdsIdList.child(advertisement.groupId!!).push().setValue(newAdKey).await()
                    groupsAdsCounterRef.child(advertisement.groupId!!)
                        .setValue(ServerValue.increment(1))

                    currentUserAdsRef.push().setValue(newAdKey).await()

                }
                Response.Success(true)
            } catch (e: Exception) {
                Response.Failure(e)
            }
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