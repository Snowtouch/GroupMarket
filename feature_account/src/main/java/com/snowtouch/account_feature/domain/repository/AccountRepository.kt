package com.snowtouch.account_feature.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Response

interface AccountRepository {

    val currentUser : FirebaseUser?
    suspend fun getUserActiveAds() : Response<List<AdvertisementPreview>>

    suspend fun getUserDraftAds() : Response<List<AdvertisementPreview>>

    suspend fun getUserFinishedAds() : Response<List<AdvertisementPreview>>

    fun signOut()
}