package com.snowtouch.feature_groups.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.getValue
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Group
import com.snowtouch.core.domain.model.GroupPreview
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.model.asResult
import com.snowtouch.core.domain.model.toGroupPreview
import com.snowtouch.core.domain.repository.DatabaseReferenceManager
import com.snowtouch.feature_groups.domain.repository.GroupsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class GroupsRepositoryImpl(
    private val auth : FirebaseAuth,
    private val dbReferences : DatabaseReferenceManager,
    private val dispatcher : CoroutineDispatcher,
) : GroupsRepository {

    override fun getUserGroupsPreviewData() : Flow<Result<List<GroupPreview>>> {
        return flow {
            val groupsIds = dbReferences.currentUserGroupsIds
                .get()
                .await()
                .children.mapNotNull { it.getValue<String>() }
            Log.d("getUserGroupsPrev", "groupsId: $groupsIds")

            if (groupsIds.isEmpty()) {
                emit(emptyList())
                return@flow
            } else {
                val groupPrevList = mutableListOf<GroupPreview>()
                for (id in groupsIds) {
                    val groupData = dbReferences.groupsPreview
                        .child(id)
                        .get()
                        .await()
                        .getValue<GroupPreview>()
                    if (groupData != null) {
                        groupPrevList.add(groupData)
                    }
                }
                Log.d("getUserGroupsPrev", "groupPrevList: $groupPrevList")
                emit(groupPrevList)
            }
        }.asResult()
    }

    override suspend fun createNewGroup(
        name : String,
        description : String,
    ) : Result<Boolean> {
        return withContext(dispatcher) {
            try {
                val newGroupKey = dbReferences.groups
                    .push()
                    .key

                val newGroup = Group(
                    uid = newGroupKey,
                    ownerId = auth.currentUser?.uid,
                    ownerName = auth.currentUser?.displayName,
                    members = null,
                    membersCount = 1,
                    name = name,
                    description = description,
                    advertisements = null,
                    advertisementsCount = 0,
                )

                if (newGroupKey != null) {
                    dbReferences.groups
                        .child(newGroupKey)
                        .setValue(newGroup)
                        .await()

                    dbReferences.groupsPreview
                        .child(newGroupKey)
                        .setValue(newGroup.toGroupPreview())
                        .await()

                    dbReferences.groupUserIdList
                        .child(newGroupKey)
                        .push()
                        .setValue(auth.currentUser?.uid)
                        .await()

                    dbReferences.groupUserNames
                        .child(newGroupKey)
                        .push()
                        .setValue(auth.currentUser?.displayName)
                        .await()

                    dbReferences.currentUserGroupsIds
                        .push()
                        .setValue(newGroupKey)
                        .await()

                    dbReferences.currentUserGroupsIdNamesPairs
                        .push()
                        .setValue(mapOf(Pair(newGroup.uid, newGroup.name)))
                        .await()

                    Result.Success(true)
                } else {
                    Result.Failure(Exception("Unknown error"))
                }
            } catch (e : Exception) {
                Result.Failure(e)
            }
        }
    }

    override fun getGroupAdvertisements(groupId : String) : Flow<Result<List<AdvertisementPreview>>> {
        return flow {
            val groupAdsId = dbReferences.groupAdsIdList
                .child(groupId)
                .get()
                .await()
                .children.mapNotNull { adId ->
                    adId.getValue<String>()
                }
            val adsPreview = mutableListOf<AdvertisementPreview>()
            if (groupAdsId.isNotEmpty()) {
                for (id in groupAdsId) {
                    val ad = dbReferences.advertisementsPreview
                        .child(id)
                        .get()
                        .await()
                        .getValue<AdvertisementPreview>()
                    if (ad != null) adsPreview.add(ad)
                }

                emit(adsPreview.toList())
            } else {
                emit(emptyList())
            }
        }.asResult()
    }
}

