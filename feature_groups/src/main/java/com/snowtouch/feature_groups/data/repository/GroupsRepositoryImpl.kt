package com.snowtouch.feature_groups.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.getValue
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Group
import com.snowtouch.core.domain.model.Response
import com.snowtouch.core.domain.repository.DatabaseReferenceManager
import com.snowtouch.feature_groups.domain.repository.GroupsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class GroupsRepositoryImpl(
    private val auth : FirebaseAuth,
    private val dbReferences : DatabaseReferenceManager,
    private val dispatcher : CoroutineDispatcher,
) : GroupsRepository {

    override suspend fun getUserGroupsPreviewData() : Response<List<Group>> {
        return withContext(dispatcher) {
            try {
                val userGroupsIdsListSnapshot = dbReferences.currentUserGroupsIds
                    .get()
                    .await()

                val groupsIdList = userGroupsIdsListSnapshot.children.mapNotNull { groupId ->
                    groupId.getValue<String>()
                }

                val userGroupsList = mutableListOf<Group>()

                if (groupsIdList.isNotEmpty()) {
                    val userGroupsSnapshot = dbReferences.groups
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
                val membersCountSnap = dbReferences.groupUsersCounter
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
                val adsCountSnap = dbReferences.groupAdsCounter
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
                val newGroupKey = dbReferences.groups
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
                    dbReferences.groups
                        .child(newGroupKey)
                        .setValue(newGroup)
                        .await()

                    dbReferences.groupUserIdList
                        .child(newGroupKey)
                        .push()
                        .setValue(auth.currentUser?.uid)
                        .await()

                    dbReferences.groupUsersCounter
                        .child(newGroupKey)
                        .setValue(1)
                        .await()

                    dbReferences.groupAdsCounter
                        .child(newGroupKey)
                        .setValue(0)
                        .await()

                    dbReferences.groupUserNames
                        .child(newGroupKey)
                        .push()
                        .setValue(auth.currentUser?.displayName)

                    dbReferences.currentUserGroupsIds
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
                val groupAdsIdSnap = dbReferences.groupAdsIdList
                    .child(groupId)
                    .get()
                    .await()

                val groupsAdsId = groupAdsIdSnap.children.mapNotNull { groupAdId ->
                    groupAdId.getValue<String>()
                }

                if (groupsAdsId.isNotEmpty()) {
                    val groupAdsPreviewSnap = dbReferences.advertisementsPreview
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