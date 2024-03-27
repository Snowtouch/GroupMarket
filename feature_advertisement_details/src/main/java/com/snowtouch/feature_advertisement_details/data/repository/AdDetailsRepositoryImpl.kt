package com.snowtouch.feature_advertisement_details.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Response
import com.snowtouch.core.domain.repository.DatabaseReferenceManager
import com.snowtouch.feature_advertisement_details.domain.repository.AdDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher

class AdDetailsRepositoryImpl(
    private val auth : FirebaseAuth,
    private val dbReferences : DatabaseReferenceManager,
    private val dispatcher : CoroutineDispatcher,
) : AdDetailsRepository {

    override suspend fun getAdDetails(adId : String) : Response<Advertisement> {
        TODO("Not yet implemented")
    }
}