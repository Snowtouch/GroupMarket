package com.snowtouch.account_feature.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.snowtouch.account_feature.domain.repository.AccountRepository
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Response
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AccountRepositoryImpl(
    db : FirebaseDatabase,
    private val auth : FirebaseAuth,
    private val dispatcher : CoroutineDispatcher,
) : AccountRepository {

    override val currentUser : FirebaseUser?
        get() = auth.currentUser

    private val userActiveAdsRef = db.getReference("users")
        .child(currentUser?.uid ?: "")
        .child("active_ads_ids")

    private val userFinishedAdsRef = db.getReference("users")
        .child(currentUser?.uid ?: "")
        .child("finished_ads_ids")

    private val advertisementsPreviewRef = db.getReference("ads_preview")

    override suspend fun getUserActiveAds() : Response<List<AdvertisementPreview>> {
        return getAdsPreview(userActiveAdsRef)
    }

    override suspend fun getUserDraftAds() : Response<List<AdvertisementPreview>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserFinishedAds() : Response<List<AdvertisementPreview>> {
        return getAdsPreview(userFinishedAdsRef)
    }

    override fun signOut() = auth.signOut()

    private suspend fun getAdsPreview(adIdRef : DatabaseReference) : Response<List<AdvertisementPreview>> {
        return withContext(dispatcher) {
            try {
                val adsIdsSnap = adIdRef.get().await()
                val adsIds = adsIdsSnap.children.mapNotNull { adId ->
                    adId.getValue<String>()
                }
                val adsPreviewSnap = advertisementsPreviewRef
                    .startAt(adsIds.first())
                    .endAt(adsIds.last())
                    .get()
                    .await()

                val adsPreview = adsPreviewSnap.children.mapNotNull { adPreview ->
                    adPreview.getValue<AdvertisementPreview>()
                }
                Response.Success(adsPreview)
            } catch (e : Exception) {
                Response.Failure(e)
            }
        }
    }
}