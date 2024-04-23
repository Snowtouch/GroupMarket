package com.snowtouch.account_feature.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.getValue
import com.snowtouch.account_feature.domain.repository.AccountRepository
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.repository.DatabaseReferenceManager
import kotlinx.coroutines.tasks.await

class AccountRepositoryImpl(
    private val dbReferences : DatabaseReferenceManager,
    private val auth : FirebaseAuth,
) : AccountRepository {

    override val currentUser : FirebaseUser?
        get() = auth.currentUser

    override suspend fun getUserActiveAds() : Result<List<AdvertisementPreview>> {
        return getAdsPreview(dbReferences.currentUserActiveAdsIds)
    }

    override suspend fun getUserDraftAds() : Result<List<AdvertisementPreview>> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserFinishedAds() : Result<List<AdvertisementPreview>> {
        return getAdsPreview(dbReferences.currentUserFinishedAdsIds)
    }

    override fun signOut() = auth.signOut()

    private suspend fun getAdsPreview(adIdRef : DatabaseReference) : Result<List<AdvertisementPreview>> {
        return try {
            val adsIdsSnap = adIdRef.get().await()
            if (!adsIdsSnap.exists() || adsIdsSnap.childrenCount == 0L) {
                return Result.Failure(Exception("No advertisements found"))
            }

            val adsIds = adsIdsSnap.children.mapNotNull { adId ->
                adId.getValue(String::class.java)
            }
            Log.d("Active ads IDs:", "$adsIds")

            if (adsIds.isEmpty()) {
                return Result.Failure(Exception("No advertisement IDs found"))
            }

            val adsPreviewSnap = dbReferences.advertisementsPreview
                .orderByKey()
                .startAt(adsIds.first())
                .endAt(adsIds.last())
                .get()
                .await()

            val adsPreview = adsPreviewSnap.children.mapNotNull { adPreview ->
                adPreview.getValue<AdvertisementPreview>()
            }

            if (adsPreview.isEmpty()) {
                return Result.Failure(Exception("No advertisement previews found"))
            }

            Result.Success(adsPreview)
        } catch (e : Exception) {
            Log.e("getAdsPreview", "Error: $e")
            Result.Failure(e)
        }
    }

}