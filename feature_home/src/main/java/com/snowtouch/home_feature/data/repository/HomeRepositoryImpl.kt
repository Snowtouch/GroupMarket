package com.snowtouch.home_feature.data.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.model.asResult
import com.snowtouch.core.domain.repository.DatabaseReferenceManager
import com.snowtouch.home_feature.domain.repository.HomeRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeRepositoryImpl(
    private val dbReferences : DatabaseReferenceManager,
) : HomeRepository {

    override fun getLatestAdsPreview() : Flow<Result<List<AdvertisementPreview>>> {
        return flow {
            val userGroupsIdList = dbReferences.currentUserGroupsIds
                .get()
                .await()
                .children.mapNotNull { id -> id.getValue<String>() }
            Log.d("HomeScreenUsrGroups", "$userGroupsIdList")
            if (userGroupsIdList.isEmpty()) {
                emit(emptyList())
                return@flow
            }

            val latestAdsIds = mutableListOf<String>()

            for (groupId in userGroupsIdList) {
                val groupAdsIdsList = dbReferences.groupAdsIdList.child(groupId)
                    .limitToLast(10)
                    .get()
                    .await()
                    .children.mapNotNull { adSnap -> adSnap.getValue<String>() }
                latestAdsIds.addAll(groupAdsIdsList)
            }
            Log.d("HomeScreenLatestAdsIds", "$latestAdsIds")

            val tenNewAdsIds = latestAdsIds
                .sorted()
                .take(10)
            Log.d("HomeScreen10LatestAds", "$latestAdsIds")

            val adPreviewList = mutableListOf<AdvertisementPreview>()
            for (id in tenNewAdsIds) {
                val ad = dbReferences.advertisementsPreview
                    .child(id)
                    .get()
                    .await()
                    .getValue<AdvertisementPreview>()
                ad?.let { adPreviewList.add(ad) }
            }
            emit(adPreviewList)
            Log.d("HomeScreenLastAdsPrev", "$adPreviewList")
        }.asResult()
    }

    override fun getRecentlyViewedAdsPreview() = callbackFlow {
        try {
            val initialPrevIds = dbReferences
                .currentUserRecentlyViewedAdsIds
                .get()
                .await()
                .children.mapNotNull { it.getValue<String>() }
            val initialPreviews = mutableListOf<AdvertisementPreview>()

            if (initialPrevIds.isNotEmpty()) {
                for (id in initialPrevIds) {
                    val preview = dbReferences
                        .advertisementsPreview
                        .child(id)
                        .get()
                        .await()
                        .getValue<AdvertisementPreview>()
                    preview?.let { initialPreviews.add(it) }
                    ensureActive()
                }
            }
            this@callbackFlow.trySend(Result.Success(initialPreviews))

            val recentAdsListener = object : ValueEventListener {
                override fun onDataChange(snapshot : DataSnapshot) {
                    val prevIds = snapshot.children.mapNotNull { it.getValue<String>() }
                    val previews = mutableListOf<AdvertisementPreview>()

                    if (prevIds.isNotEmpty()) {
                        prevIds.forEach { id ->
                            this@callbackFlow.launch {
                                val preview = dbReferences
                                    .advertisementsPreview
                                    .child(id)
                                    .get()
                                    .await()
                                    .getValue<AdvertisementPreview>()
                                ensureActive()
                                preview?.let { previews.add(it) }
                            }
                        }
                    }
                    this@callbackFlow.trySend(Result.Success(previews))
                }

                override fun onCancelled(error : DatabaseError) {
                    this@callbackFlow.trySend(Result.Failure(error.toException()))
                }
            }
            dbReferences.currentUserRecentlyViewedAdsIds.addValueEventListener(recentAdsListener)

            awaitClose {
                dbReferences.currentUserRecentlyViewedAdsIds.removeEventListener(recentAdsListener)
            }
        } catch (e : Exception) {
            this@callbackFlow.trySend(Result.Failure(e))
        }
    }

    override fun getUserFavoriteAdsPreview(favoriteAdsIds : List<String>) = callbackFlow {
        try {

        } catch (e : Exception) {
            this@callbackFlow.trySend(Result.Failure(e))
        }
    }
}