package com.snowtouch.groupmarket.model.service.impl

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.snowtouch.groupmarket.model.Advertisement
import com.snowtouch.groupmarket.model.Group
import com.snowtouch.groupmarket.model.User
import com.snowtouch.groupmarket.model.service.AccountService
import com.snowtouch.groupmarket.model.service.DatabaseService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DatabaseServiceImpl(
    private val auth: AccountService,
    private val firebaseDatabase: FirebaseDatabase,
    private val dispatcher: CoroutineDispatcher
) : DatabaseService {

    private val currentUserReference = firebaseDatabase.getReference("users").child(auth.currentUserId)
    private val usersReference = firebaseDatabase.getReference("users")
    private val adsReference = firebaseDatabase.getReference("ads")
    private val groupsReference = firebaseDatabase.getReference("groups")

    private val _userData = MutableStateFlow<User?>(null)
    override val userData: StateFlow<User?>
        get() = _userData

    private val _userGroupsData = MutableStateFlow<List<Group?>>(emptyList())
    override val userGroupsData: StateFlow<List<Group?>>
        get() = _userGroupsData

    private val userDataListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val newData = snapshot.getValue<User?>()
            _userData.value = newData
        }

        override fun onCancelled(error: DatabaseError) {
            throw Exception(error.message)
        }
    }

    private val userGroupDataListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val newData = snapshot.getValue<Group>()

            val tempGroup = _userGroupsData.value.toMutableList()
            val indexOfUpdatedGroup = tempGroup.indexOfFirst { group ->
                group?.uid == newData?.uid }
            if (indexOfUpdatedGroup != -1) {
                tempGroup[indexOfUpdatedGroup] = newData
                _userGroupsData.value = tempGroup.toList()
            }
        }

        override fun onCancelled(error: DatabaseError) {
            throw Exception(error.message)
        }
    }

    override suspend fun enableUserGroupsDataListeners() {
        val userHaveGroups = userData.value?.groups?.isNotEmpty() == true && userData.value?.groups != null
        withContext(dispatcher) {
            if (userHaveGroups) {
                for (groupId in userData.value?.groups!!)
                    groupsReference
                        .child(groupId)
                        .addValueEventListener(userGroupDataListener)
            }
        }
    }

    override suspend fun disableUserGroupsDataListeners() {
        val userHaveGroups = userData.value?.groups?.isNotEmpty() == true && userData.value?.groups != null
        withContext(dispatcher) {
            if (userHaveGroups) {
                for (groupId in userData.value?.groups!!)
                    groupsReference
                        .child(groupId)
                        .removeEventListener(userGroupDataListener)
            }
        }
    }

    override suspend fun enableUserDataListener() {
        withContext(dispatcher) {
            currentUserReference
                .addValueEventListener(userDataListener)
        }
    }

    override suspend fun disableUserDataListener() {
        withContext(dispatcher) {
            currentUserReference
                .removeEventListener(userDataListener)
        }
    }

    override suspend fun createNewUserData(user: User) {
        withContext(dispatcher) {
            usersReference
                .child(auth.currentUserId)
                .setValue(user)
        }
    }

    override suspend fun createNewGroup(name: String, description: String, isPrivate: Boolean) {
        withContext(dispatcher) {

            val newKey = adsReference.push().key ?: ""
            val newGroup = Group(
                uid = newKey,
                ownerId = auth.currentUserId,
                ownerName = userData.value?.name ?: "",
                name = name,
                description = description
            )

            groupsReference.child(newKey).setValue(newGroup)

            val currentGroupsList = mutableListOf<String>()

            val groupsSnapshot = currentUserReference
                .child("groups")
                .get()
                .await()

            for (groupUidSnapshot in groupsSnapshot.children) {
                val groupUid = groupUidSnapshot.getValue<String>()
                currentGroupsList.add(groupUid ?: "")
            }

            currentGroupsList.add(newKey)

            usersReference.child(auth.currentUserId)
                .child("groups")
                .setValue(currentGroupsList)
        }
    }

    override suspend fun getNewAdReferenceKey(): String? {
        return withContext(dispatcher) {
            adsReference.push().key
        }
    }

    override suspend fun getUserGroupsNames(): List<String> {
        val groupUidList = _userData.value?.groups.orEmpty()

        return if (groupUidList.isNotEmpty()) {
            withContext(dispatcher) {
                groupUidList.map { groupUid ->
                    groupsReference.child(groupUid).child("name").get().await().value as String
                }
            }
        } else {
            emptyList()
        }
    }
    override suspend fun createAdvertisement(advertisement: Advertisement, ref: String) {
        withContext(dispatcher){
            val adWithNewKey = advertisement.copy(ownerUid = auth.currentUserId)
            val advertisementValues = adWithNewKey.toMap()

            val childUpdates = hashMapOf<String, Any>(
                "/ads/$ref" to advertisementValues,
                "/users/${auth.currentUserId}/advertisements/$ref" to advertisementValues,
                "/groups/${advertisement.groupId}/$ref" to advertisementValues
            )
            firebaseDatabase.reference.updateChildren(childUpdates).await()
        }
    }

    override suspend fun addOrRemoveFavoriteAd(adId: String) {
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


    override suspend fun getLatestAdvertisementsList(): List<Advertisement> = withContext(dispatcher) {

        val advertisementsList = mutableListOf<Advertisement>()

        val newestAds = adsReference
            .limitToFirst(10)
            .get()
            .await()

        for (adSnapshot in newestAds.children) {
            val advertisement = adSnapshot.getValue(Advertisement::class.java)
            advertisement?.let {
                advertisementsList.add(it)
            }
        }
        return@withContext advertisementsList
    }

    override suspend fun getUserFavoriteAdvertisementsList(): List<Advertisement> = withContext(dispatcher) {

        val favoritesList = _userData.value?.favoritesList.orEmpty()

        if (favoritesList.isNotEmpty()) {
            val favoriteAdsSnapshot = adsReference
                .orderByKey()
                .startAt(favoritesList.first())
                .endAt(favoritesList.last())
                .get()
                .await()

            return@withContext favoriteAdsSnapshot.children.mapNotNull { adSnapshot ->
                adSnapshot.getValue(Advertisement::class.java)
            }
        }
        return@withContext emptyList()
    }

    override suspend fun getAdvertisementDetails(
        advertisementUid: String
    ): Advertisement = withContext(dispatcher) {

        val adDetailsSnapshot = adsReference
            .child(advertisementUid)
            .get()
            .await()

        return@withContext adDetailsSnapshot.getValue<Advertisement?>(Advertisement::class.java)
            ?: Advertisement()
    }
}