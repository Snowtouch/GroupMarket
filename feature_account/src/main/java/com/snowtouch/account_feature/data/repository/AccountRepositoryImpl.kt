package com.snowtouch.account_feature.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.getValue
import com.snowtouch.account_feature.domain.repository.AccountRepository
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.repository.DatabaseReferenceManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AccountRepositoryImpl(
    private val dbReferences : DatabaseReferenceManager,
    private val auth : FirebaseAuth,
    private val dispatcher : CoroutineDispatcher,
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
        return withContext(dispatcher) {
            try {
                val adsIdsSnap = adIdRef.get().await()
                val adsIds = adsIdsSnap.children.mapNotNull { adId ->
                    adId.getValue<String>()
                }
                val adsPreviewSnap = dbReferences.advertisementsPreview
                    .startAt(adsIds.first())
                    .endAt(adsIds.last())
                    .get()
                    .await()

                val adsPreview = adsPreviewSnap.children.mapNotNull { adPreview ->
                    adPreview.getValue<AdvertisementPreview>()
                }
                Result.Success(adsPreview)
            } catch (e : Exception) {
                Result.Failure(e)
            }
        }
    }
}