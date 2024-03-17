package com.snowtouch.feature_groups.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.snowtouch.feature_groups.domain.repository.GroupsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class GroupsRepositoryImpl(
    private val auth : FirebaseAuth,
    db : FirebaseDatabase,
    private val dispatcher : CoroutineDispatcher,
) : GroupsRepository {

    private val groupsRef = db.getReference("groups")
    private val groupsUsersCounterRef = db.getReference("groups_users_counter")
    private val groupsAdsCounterRef = db.getReference("groups_ads_counter")
    private val groupsUserListRef = db.getReference("groups_userId_list")
    private val groupsUserNamesRef = db.getReference("groups_userNames_list")
    private val groupsAdvertisementsList = db.getReference("groups_adsId_list")

    private val userGroupsReference = db.getReference("user_groups")

    private val advertisementsPreviewRef = db.getReference("ads_preview")

    override suspend fun getUserGroupsPreviewData() : com.snowtouch.core.domain.model.Response<List<com.snowtouch.core.domain.model.Group>> {
        return withContext(dispatcher) {
            try {
                val userGroupsIdsListSnapshot = userGroupsReference
                    .child(auth.currentUser?.uid!!)
                    .get()
                    .await()

                val groupsIdList = userGroupsIdsListSnapshot.children.mapNotNull { groupId ->
                    groupId.getValue<String>()
                }

                val userGroupsList = mutableListOf<com.snowtouch.core.domain.model.Group>()

                if (groupsIdList.isNotEmpty()) {
                    val userGroupsSnapshot = groupsRef
                        .orderByKey()
                        .startAt(groupsIdList.first())
                        .endAt(groupsIdList.last())
                        .get()
                        .await()

                    userGroupsSnapshot.children.forEach { group ->
                        userGroupsList.add(
                            group.getValue<com.snowtouch.core.domain.model.Group>()
                                ?: com.snowtouch.core.domain.model.Group()
                        )
                    }
                }
                com.snowtouch.core.domain.model.Response.Success(userGroupsList)
            } catch (e : Exception) {
                com.snowtouch.core.domain.model.Response.Failure(e)
            }
        }
    }

    override suspend fun getGroupUsersCount(groupId : String) : com.snowtouch.core.domain.model.Response<Int> {
        return withContext(dispatcher) {
            try {
                val membersCountSnap = groupsUsersCounterRef
                    .child(groupId)
                    .get()
                    .await()

                val membersCount = membersCountSnap.getValue<Int>()

                com.snowtouch.core.domain.model.Response.Success(membersCount)
            } catch (e : Exception) {
                com.snowtouch.core.domain.model.Response.Failure(e)
            }
        }
    }

    override suspend fun getGroupAdsCount(groupId : String) : com.snowtouch.core.domain.model.Response<Int> {
        return withContext(dispatcher) {
            try {
                val adsCountSnap = groupsAdsCounterRef
                    .child(groupId)
                    .get()
                    .await()

                val adsCount = adsCountSnap.getValue<Int>()

                com.snowtouch.core.domain.model.Response.Success(adsCount)
            } catch (e : Exception) {
                com.snowtouch.core.domain.model.Response.Failure(e)
            }
        }
    }

    override suspend fun createNewGroup(
        name : String,
        description : String,
    ) : com.snowtouch.core.domain.model.Response<Boolean> {
        return withContext(dispatcher) {
            try {
                val newGroupKey = groupsRef
                    .push()
                    .key

                val newGroup = com.snowtouch.core.domain.model.Group(
                    uid = newGroupKey,
                    ownerId = auth.currentUser?.uid,
                    ownerName = auth.currentUser?.displayName,
                    members = null,
                    name = name,
                    description = description,
                    advertisements = null
                )

                if (newGroupKey != null) {
                    groupsRef
                        .child(newGroupKey)
                        .setValue(newGroup)
                        .await()

                    groupsUserListRef
                        .child(newGroupKey)
                        .push()
                        .setValue(auth.currentUser?.uid)
                        .await()

                    groupsUsersCounterRef
                        .child(newGroupKey)
                        .setValue(1)
                        .await()

                    groupsAdsCounterRef
                        .child(newGroupKey)
                        .setValue(0)
                        .await()

                    groupsUserNamesRef
                        .child(newGroupKey)
                        .push()
                        .setValue(auth.currentUser?.displayName)

                    userGroupsReference
                        .child(auth.currentUser?.uid!!)
                        .push()
                        .setValue(newGroupKey)
                        .await()

                    com.snowtouch.core.domain.model.Response.Success(true)
                } else {
                    com.snowtouch.core.domain.model.Response.Failure(Exception("Unknown error"))
                }
            } catch (e : Exception) {
                com.snowtouch.core.domain.model.Response.Failure(e)
            }
        }
    }

    override suspend fun getGroupAdvertisements(groupId : String) : com.snowtouch.core.domain.model.Response<List<com.snowtouch.core.domain.model.AdvertisementPreview>> {
        return withContext(dispatcher) {
            try {
                val groupAdsPreviewList =
                    mutableListOf<com.snowtouch.core.domain.model.AdvertisementPreview>()
                val groupAdsIdSnap = groupsAdvertisementsList
                    .child(groupId)
                    .get()
                    .await()

                val groupsAdsId = groupAdsIdSnap.children.mapNotNull { groupAdId ->
                    groupAdId.getValue<String>()
                }

                if (groupsAdsId.isNotEmpty()) {
                    val groupAdsPreviewSnap = advertisementsPreviewRef
                        .orderByKey()
                        .startAt(groupsAdsId.first())
                        .endAt(groupsAdsId.last())
                        .get()
                        .await()

                    groupAdsPreviewSnap.children.map { ad ->
                        groupAdsPreviewList
                            .add(ad.getValue<com.snowtouch.core.domain.model.AdvertisementPreview>()!!)
                    }
                }
                com.snowtouch.core.domain.model.Response.Success(groupAdsPreviewList)
            } catch (e : Exception) {
                com.snowtouch.core.domain.model.Response.Failure(e)
            }
        }
    }
}