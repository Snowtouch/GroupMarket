package com.snowtouch.home_feature.data.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.getValue
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Response
import com.snowtouch.core.domain.repository.DatabaseReferenceManager
import com.snowtouch.home_feature.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeRepositoryImpl(
    private val dbReferences : DatabaseReferenceManager,
    private val dispatcher : CoroutineDispatcher
) : HomeRepository {

    override suspend fun getLatestAdsPreview() : Response<List<AdvertisementPreview>> {
        return withContext(dispatcher) {
            try {
                val userGroupsIdListSnap = dbReferences.currentUserGroupsIds.get().await()
                val userGroupsIdList = userGroupsIdListSnap.children.mapNotNull { id ->
                    id.getValue<String>()
                }

                val latestAdsIds = mutableListOf<String>()
                for (groupId in userGroupsIdList) {
                    val groupAdsIdsSnap = dbReferences
                        .groupAdsIdList.child(groupId)
                        .limitToLast(10)
                        .get()
                        .await()

                    val groupAdsIdsList = groupAdsIdsSnap.children.mapNotNull { adSnap ->
                        adSnap.getValue<String>()
                    }
                    latestAdsIds.addAll(groupAdsIdsList)
                }
                val tenNewAdsIds = latestAdsIds
                    .sortedDescending()
                    .take(10)

                val newestAdsPreviewSnap = dbReferences.advertisementsPreview
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
        return getAdsPreview(dbReferences.currentUserRecentAdsIds)
    }

    override suspend fun getUserFavoriteAdsPreview() : Response<List<AdvertisementPreview>> {
        return getAdsPreview(dbReferences.currentUserFavoriteAdsIds)
    }

    private suspend fun getAdsPreview(adIdRef : DatabaseReference) : Response<List<AdvertisementPreview>> {
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
                Response.Success(adsPreview)
            } catch (e : Exception) {
                Response.Failure(e)
            }
        }
    }
}