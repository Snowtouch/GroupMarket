package com.snowtouch.groupmarket.model.service.impl

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.snowtouch.auth_feature.domain.repository.AuthRepository
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Group
import com.snowtouch.core.domain.model.User
import com.snowtouch.core.domain.repository.DatabaseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DatabaseRepositoryImpl(
    private val auth: com.snowtouch.auth_feature.domain.repository.AuthRepository,
    private val firebaseDatabase: FirebaseDatabase,
    private val dispatcher: CoroutineDispatcher
) : com.snowtouch.core.domain.repository.DatabaseRepository {

    private val currentUserReference = firebaseDatabase.getReference("users").child(auth.currentUser?.uid?: "")
    private val usersReference = firebaseDatabase.getReference("users")
    private val adsReference = firebaseDatabase.getReference("ads")
    private val groupsReference = firebaseDatabase.getReference("groups")

    private val _userData = MutableStateFlow<com.snowtouch.core.domain.model.User?>(null)
    override val userData: StateFlow<com.snowtouch.core.domain.model.User?>
        get() = _userData

    private val _userGroupsData = MutableStateFlow<List<com.snowtouch.core.domain.model.Group?>>(emptyList())
    override val userGroupsData: StateFlow<List<com.snowtouch.core.domain.model.Group?>>
        get() = _userGroupsData

    private val userDataListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val newData = snapshot.getValue<com.snowtouch.core.domain.model.User?>()
            _userData.value = newData
        }

        override fun onCancelled(error: DatabaseError) {
            throw Exception(error.message)
        }
    }

    private val userGroupDataListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val newGroupData = snapshot.getValue<com.snowtouch.core.domain.model.Group>()

            val tempGroups = _userGroupsData.value.toMutableList()
            val existingGroupIndex = tempGroups.indexOfFirst { group ->
                group?.uid == newGroupData?.uid
            }
            if (existingGroupIndex != -1) {
                tempGroups[existingGroupIndex] = newGroupData
            } else {
                tempGroups.add(newGroupData)
            }
            _userGroupsData.value = tempGroups.toList()
        }

        override fun onCancelled(error: DatabaseError) {
            throw Exception(error.message)
        }
    }

    override suspend fun getInitialUserData() {
        withContext(dispatcher) {
            val currentDataSnapshot = currentUserReference
                .get()
                .await()
            val currentUserData = currentDataSnapshot.getValue<com.snowtouch.core.domain.model.User?>()
            _userData.value = currentUserData
            Log.e("InitialUserData", "data recieved")
        }
    }

    override suspend fun getInitialUserGroupsData() {
        withContext(dispatcher) {
            val groupsIdList = _userData.value?.groups ?: emptyList()

            if (groupsIdList.isNotEmpty()) {
                val groupsSnapshot = groupsReference
                    .orderByKey()
                    .startAt(groupsIdList.first())
                    .endAt(groupsIdList.last())
                    .get()
                    .await()

                _userGroupsData.value = groupsSnapshot.children.mapNotNull { groupSnapshot ->
                    groupSnapshot.getValue(com.snowtouch.core.domain.model.Group::class.java)
                }
                Log.e("InitialUserGroupsData", "populated list")
            } else {
                _userGroupsData.value = emptyList()
                Log.e("InitialUserGroupsData", "Empty List")
            }
        }
    }


    override suspend fun enableUserGroupsDataListeners() {
        val userHaveGroups = userData.value?.groups?.isNotEmpty() == true && userData.value?.groups != null
        withContext(dispatcher) {
            if (userHaveGroups) {
                val userGroupsList = userData.value?.groups?: emptyList()
                for (groupId in userGroupsList)
                    groupsReference
                        .child(groupId)
                        .addValueEventListener(userGroupDataListener)
                Log.e("UserGroupsListener", "enabled")
            }
        }

    }

    override suspend fun disableUserGroupsDataListeners() {
        val userHaveGroups = userData.value?.groups?.isNotEmpty() == true && userData.value?.groups != null
        withContext(dispatcher) {
            if (userHaveGroups) {
                val userGroupsList = userData.value?.groups?: emptyList()
                for (groupId in userGroupsList)
                    groupsReference
                        .child(groupId)
                        .removeEventListener(userGroupDataListener)
                Log.e("UserGroupsListener", "disabled")
            }
        }
    }

    override suspend fun enableUserDataListener() {
        withContext(dispatcher) {
            currentUserReference
                .addValueEventListener(userDataListener)
            Log.e("UserDataListener", "enabled")
        }
    }

    override suspend fun disableUserDataListener() {
        withContext(dispatcher) {
            currentUserReference
                .removeEventListener(userDataListener)
            Log.e("UserDataListener", "disabled")
        }
    }

    override suspend fun createNewUserData(email: String, name: String) {
        withContext(dispatcher) {
            val newUser = com.snowtouch.core.domain.model.User(
                uid = auth.currentUser?.uid ?: "",
                email = email,
                name = name,
                groups = emptyList(),
                advertisements = emptyList(),
                favoritesList = emptyList(),
                recentlyWatched = emptyList()
            )
            usersReference
                .child(auth.currentUser?.uid?: "")
                .setValue(newUser)
        }
    }

    override suspend fun createNewGroup(name: String, description: String, isPrivate: Boolean) {
        try {
            withContext(dispatcher) {
                val membersList = listOf(auth.currentUser?.uid?: "")
                val newKey = groupsReference.push().key ?: ""

                val newGroup = com.snowtouch.core.domain.model.Group(
                    uid = newKey,
                    ownerId = auth.currentUser?.uid ?: "",
                    members = membersList,
                    ownerName = _userData.value?.name ?: "",
                    name = name,
                    description = description
                )

                groupsReference.child(newKey).setValue(newGroup).await()

                val updatedGroupList = mutableListOf<String>()

                if (_userData.value?.groups?.isEmpty() == false) {
                    updatedGroupList.addAll(_userData.value?.groups!!)
                    val userGroupList = currentUserReference
                        .child("groups")
                        .get()
                        .await()
                    userGroupList.children.forEach {
                        updatedGroupList.add(it.getValue<String>()!!)
                    }
                } else {
                    updatedGroupList.add(newKey)
                }
                currentUserReference
                    .child("groups")
                    .setValue(updatedGroupList.toList())
            }
        } catch (e: Exception) {
            Log.e("createNewGroupError", "Error creating new group", e)
        }
    }

    override suspend fun getNewAdReferenceKey(): String? {
        return withContext(dispatcher) {
            adsReference.push().key
        }
    }

    override suspend fun getGroupAdvertisementsList(groupId: String): List<com.snowtouch.core.domain.model.Advertisement> = withContext(dispatcher) {
        val groupAdsIdListSnapshot = groupsReference
            .child(groupId)
            .get()
            .await()

        val groupAdsIdList = mutableListOf<String>()

        groupAdsIdListSnapshot.children.forEach { childSnapshot ->
            val adId = childSnapshot.getValue(String::class.java)
            adId?.let {
                groupAdsIdList.add(it)
            }
        }
        if (groupAdsIdList.isNotEmpty()) {
            val groupAdsSnapshot = adsReference
                .orderByKey()
                .startAt(groupAdsIdList.first())
                .endAt(groupAdsIdList.last())
                .get()
                .await()

            return@withContext groupAdsSnapshot.children.mapNotNull { adsSnapshot ->
                adsSnapshot.getValue(com.snowtouch.core.domain.model.Advertisement::class.java)
            }
        }

        return@withContext emptyList()
    }

    override suspend fun createAdvertisement(advertisement: com.snowtouch.core.domain.model.Advertisement, ref: String) {
        withContext(dispatcher){
            val adWithNewKey = advertisement.copy(ownerUid = auth.currentUser?.uid?: "")

            adsReference
                .child(advertisement.uid!!)
                .setValue(advertisement)
                .await()

            val currentUserAdsListSnapshot = currentUserReference
                .child("advertisements")
                .get()
                .await()

            val userAdsIdList = mutableListOf<String>()
            currentUserAdsListSnapshot.children.forEach { childSnapshot ->
                val adId = childSnapshot.getValue(String::class.java)
                adId?.let {
                    userAdsIdList.add(it)
                }
            }
            userAdsIdList.add(adWithNewKey.uid!!)

            currentUserReference
                .child("advertisements")
                .setValue(userAdsIdList)
        }
    }

    override suspend fun toggleFavoriteAd(adId: String) {
        withContext(dispatcher) {

            val favoritesListReference = currentUserReference.child("favoritesList")
            val currentList = _userData.value?.favoritesList?.toMutableList() ?: mutableListOf()

            if (currentList.contains(adId)) {
                currentList.remove(adId)
            } else {
                currentList.add(adId)
            }
            val updateMap = mapOf<String, Any>("favoritesList" to currentList)

            favoritesListReference.updateChildren(updateMap).await()
        }
    }


    override suspend fun getLatestAdvertisementsList(): List<com.snowtouch.core.domain.model.Advertisement> = withContext(dispatcher) {

        val advertisementsList = mutableListOf<com.snowtouch.core.domain.model.Advertisement>()

        val newestAds = adsReference
            .limitToFirst(10)
            .get()
            .await()

        for (adSnapshot in newestAds.children) {
            val advertisement = adSnapshot.getValue(com.snowtouch.core.domain.model.Advertisement::class.java)
            advertisement?.let {
                advertisementsList.add(it)
            }
        }
        return@withContext advertisementsList
    }

    override suspend fun getUserFavoriteAdvertisementsList(): List<com.snowtouch.core.domain.model.Advertisement> = withContext(dispatcher) {

        val favoritesList = _userData.value?.favoritesList.orEmpty()

        if (favoritesList.isNotEmpty()) {
            val favoriteAdsSnapshot = adsReference
                .orderByKey()
                .startAt(favoritesList.first())
                .endAt(favoritesList.last())
                .get()
                .await()

            return@withContext favoriteAdsSnapshot.children.mapNotNull { adSnapshot ->
                adSnapshot.getValue(com.snowtouch.core.domain.model.Advertisement::class.java)
            }
        }
        return@withContext emptyList()
    }

    override suspend fun getAdvertisementDetails(
        advertisementUid: String
    ): com.snowtouch.core.domain.model.Advertisement = withContext(dispatcher) {

        val adDetailsSnapshot = adsReference
            .child(advertisementUid)
            .get()
            .await()

        return@withContext adDetailsSnapshot.getValue<com.snowtouch.core.domain.model.Advertisement?>(
            com.snowtouch.core.domain.model.Advertisement::class.java)
            ?: com.snowtouch.core.domain.model.Advertisement()
    }
}