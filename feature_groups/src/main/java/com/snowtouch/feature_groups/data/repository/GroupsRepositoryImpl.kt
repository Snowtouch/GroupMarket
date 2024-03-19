package com.snowtouch.feature_groups.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.getValue
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Group
import com.snowtouch.core.domain.model.Response
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
    private val groupsAdsIdList = db.getReference("groups_adsId_list")

    private val userGroupsReference = db.getReference("user_groups")

    private val advertisementsPreviewRef = db.getReference("ads_preview")

    override suspend fun getUserGroupsPreviewData() : Response<List<Group>> {
        return withContext(dispatcher) {
            try {
                val userGroupsIdsListSnapshot = userGroupsReference
                    .child(auth.currentUser?.uid!!)
                    .get()
                    .await()

                val groupsIdList = userGroupsIdsListSnapshot.children.mapNotNull { groupId ->
                    groupId.getValue<String>()
                }

                val userGroupsList = mutableListOf<Group>()

                if (groupsIdList.isNotEmpty()) {
                    val userGroupsSnapshot = groupsRef
                        .orderByKey()
                        .startAt(groupsIdList.first())
                        .endAt(groupsIdList.last())
                        .get()
                        .await()

                    userGroupsSnapshot.children.forEach { group ->
                        userGroupsList.add(
                            group.getValue<Group>()
                                ?: Group()
                        )
                    }
                }
                Response.Success(userGroupsList)
            } catch (e : Exception) {
                Response.Failure(e)
            }
        }
    }

    override suspend fun getGroupUsersCount(groupId : String) : Response<Int> {
        return withContext(dispatcher) {
            try {
                val membersCountSnap = groupsUsersCounterRef
                    .child(groupId)
                    .get()
                    .await()

                val membersCount = membersCountSnap.getValue<Int>()

                Response.Success(membersCount)
            } catch (e : Exception) {
                Response.Failure(e)
            }
        }
    }

    override suspend fun getGroupAdsCount(groupId : String) : Response<Int> {
        return withContext(dispatcher) {
            try {
                val adsCountSnap = groupsAdsCounterRef
                    .child(groupId)
                    .get()
                    .await()

                val adsCount = adsCountSnap.getValue<Int>()

                Response.Success(adsCount)
            } catch (e : Exception) {
                Response.Failure(e)
            }
        }
    }

    override suspend fun createNewGroup(
        name : String,
        description : String,
    ) : Response<Boolean> {
        return withContext(dispatcher) {
            try {
                val newGroupKey = groupsRef
                    .push()
                    .key

                val newGroup = Group(
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

                    Response.Success(true)
                } else {
                    Response.Failure(Exception("Unknown error"))
                }
            } catch (e : Exception) {
                Response.Failure(e)
            }
        }
    }

    override suspend fun getGroupAdvertisements(groupId : String) : Response<List<AdvertisementPreview>> {
        return withContext(dispatcher) {
            try {
                val groupAdsPreviewList =
                    mutableListOf<AdvertisementPreview>()
                val groupAdsIdSnap = groupsAdsIdList
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
                            .add(ad.getValue<AdvertisementPreview>()!!)
                    }
                }
                Response.Success(groupAdsPreviewList)
            } catch (e : Exception) {
                Response.Failure(e)
            }
        }
    }
}