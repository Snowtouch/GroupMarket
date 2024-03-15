package com.snowtouch.groupmarket.home.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.snowtouch.groupmarket.core.domain.model.AdvertisementPreview
import com.snowtouch.groupmarket.core.domain.model.Response
import com.snowtouch.groupmarket.home.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeRepositoryImpl(
    db: FirebaseDatabase,
    auth: FirebaseAuth,
    private val dispatcher: CoroutineDispatcher
) : HomeRepository {

    private val advertisementsPreviewRef = db.getReference("ads_preview")
    private val userGroupsRef = db.getReference("user_groups").child(auth.currentUser?.uid!!)
    private val userRecentAdsRef = db.getReference("user_recent").child(auth.currentUser?.uid!!)
    private val userFavoriteAdsRef = db.getReference("user_favorite").child(auth.currentUser?.uid!!)
    private val groupAdsListRef = db.getReference("groups_adsId_list")

    override suspend fun getLatestAds() : Response<List<AdvertisementPreview>> {
        return withContext(dispatcher) {
            try {
                val userGroupsIdListSnap = userGroupsRef.get().await()
                val userGroupsIdList = userGroupsIdListSnap.children.mapNotNull { id ->
                    id.getValue<String>()
                }

                val latestAdsIds = mutableListOf<String>()
                for (groupId in userGroupsIdList) {
                    val groupAdsIdsSnap = groupAdsListRef.child(groupId).limitToLast(10).get().await()
                    val groupAdsIdsList = groupAdsIdsSnap.children.mapNotNull { adSnap ->
                        adSnap.getValue<String>()
                    }
                    latestAdsIds.addAll(groupAdsIdsList)
                }
                val tenNewAdsIds = latestAdsIds
                    .sortedDescending()
                    .take(10)

                val newestAdsPreviewSnap = advertisementsPreviewRef
                    .startAt(tenNewAdsIds.first())
                    .endAt(tenNewAdsIds.last())
                    .get()
                    .await()

                val newestAdsPreview = newestAdsPreviewSnap.children.mapNotNull { adSnap ->
                    adSnap.getValue<AdvertisementPreview>()
                }

                Response.Success(newestAdsPreview)
            } catch (e: Exception) {
                Response.Failure(e)
            }
        }
    }

    override suspend fun getRecentlyViewedAdsPreview() : Response<List<AdvertisementPreview>> {
        return getAdsPreview(userRecentAdsRef)
    }

    override suspend fun getUserFavoriteAdsPreview() : Response<List<AdvertisementPreview>> {
        return getAdsPreview(userFavoriteAdsRef)
    }

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