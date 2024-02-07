package com.snowtouch.groupmarket.model.service.impl

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.snowtouch.groupmarket.model.Advertisement
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
    override val userData: StateFlow<User?> get() = _userData

    override suspend fun enableUserDataListener(onError: (Throwable?) -> Unit) {
        withContext(dispatcher) {
            currentUserReference
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val newData = snapshot.getValue<User?>()
                        _userData.value = newData
                    }

                    override fun onCancelled(error: DatabaseError) {
                        onError(Exception(error.message))
                    }
                })
        }
    }

    override suspend fun createNewUserData(user: User) {
        withContext(dispatcher) {
            usersReference
                .child(auth.currentUserId)
                .setValue(user)
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
        try {
            val newestAds = adsReference
                .limitToFirst(10)
                .get()
                .await()

            val advertisementsList = mutableListOf<Advertisement>()

            for (adSnapshot in newestAds.children) {
                val advertisement = adSnapshot.getValue(Advertisement::class.java)
                advertisement?.let {
                    advertisementsList.add(it)
                }
            }

            return@withContext advertisementsList
        } catch (e: Exception) {
            return@withContext emptyList()
        }
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
}