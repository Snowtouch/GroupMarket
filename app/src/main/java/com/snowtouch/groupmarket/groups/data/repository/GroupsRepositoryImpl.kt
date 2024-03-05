package com.snowtouch.groupmarket.groups.data.repository

import com.google.firebase.database.FirebaseDatabase
import com.snowtouch.groupmarket.auth.domain.repository.AuthRepository
import com.snowtouch.groupmarket.core.domain.model.Advertisement
import com.snowtouch.groupmarket.core.domain.model.Group
import com.snowtouch.groupmarket.core.domain.model.Response
import com.snowtouch.groupmarket.groups.domain.repository.GroupsRepository
import kotlinx.coroutines.CoroutineDispatcher

class GroupsRepositoryImpl(
    private val auth: AuthRepository,
    private val firebaseDatabase: FirebaseDatabase,
    private val dispatcher: CoroutineDispatcher
) : GroupsRepository {

    override suspend fun getUserGroups(userId : String) : Response<List<Group>> {
        TODO("Not yet implemented")
    }

    override suspend fun createNewGroup() : Response<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getGroupAdvertisements(groupId : String) : Response<List<Advertisement>> {
        TODO("Not yet implemented")
    }
}