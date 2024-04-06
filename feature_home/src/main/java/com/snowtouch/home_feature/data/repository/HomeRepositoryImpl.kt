package com.snowtouch.home_feature.data.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.getValue
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.model.asResult
import com.snowtouch.core.domain.repository.DatabaseReferenceManager
import com.snowtouch.home_feature.domain.repository.HomeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeRepositoryImpl(
    private val dbReferences : DatabaseReferenceManager,
    private val dispatcher : CoroutineDispatcher,
) : HomeRepository {

    override fun getLatestAdsPreview(): Flow<Result<List<AdvertisementPreview>>> {
        return flow {
        val userGroupsIdList = withContext(dispatcher) {
            dbReferences.currentUserGroupsIds.get().await()
                .children.mapNotNull { id ->
                    id.getValue<String>()
                }
        }
        if (userGroupsIdList.isEmpty()) {
            emit(emptyList())
            return@flow
        }

        val latestAdsIds = mutableListOf<String>()
        for (groupId in userGroupsIdList) {
            val groupAdsIdsSnap = withContext(dispatcher) {
                dbReferences.groupAdsIdList.child(groupId)
                    .limitToLast(10)
                    .get()
                    .await()
            }

            val groupAdsIdsList = groupAdsIdsSnap.children.mapNotNull { adSnap ->
                adSnap.getValue<String>()
            }
            latestAdsIds.addAll(groupAdsIdsList)
        }

        val tenNewAdsIds = latestAdsIds
            .sortedDescending()
            .take(10)

        val newestAdsPreviewSnap = withContext(dispatcher) {
            dbReferences.advertisementsPreview
                .startAt(tenNewAdsIds.first())
                .endAt(tenNewAdsIds.last())
                .get()
                .await()
        }

        val newestAdsPreview = newestAdsPreviewSnap.children.mapNotNull { adSnap ->
            adSnap.getValue<AdvertisementPreview>()
        }
            emit(newestAdsPreview)
        }.asResult()
    }

    override fun getRecentlyViewedAdsPreview() : Flow<Result<List<AdvertisementPreview>>> {
        return getAdsPreview(dbReferences.currentUserRecentAdsIds)
    }

    override fun getUserFavoriteAdsPreview() : Flow<Result<List<AdvertisementPreview>>> {
        return getAdsPreview(dbReferences.currentUserFavoriteAdsIds)
    }

    private fun getAdsPreview(adIdRef : DatabaseReference) : Flow<Result<List<AdvertisementPreview>>> {
        return flow {
            val adsIdsSnap = adIdRef.get().await()
            val adsIds = adsIdsSnap.children.mapNotNull { adId ->
                adId.getValue<String>()
            }
            if (adsIds.isNotEmpty()) {
                val adsPreview = dbReferences.advertisementsPreview
                    .startAt(adsIds.first())
                    .endAt(adsIds.last())
                    .get()
                    .await()
                    .children.mapNotNull { ad ->
                        ad.getValue<AdvertisementPreview>()
                    }
                emit(adsPreview)
            } else {
                emit(emptyList())
            }
        }.asResult()
    }
}