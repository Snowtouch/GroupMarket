package com.snowtouch.feature_new_advertisement.domain.repository

import android.net.Uri
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Response
import com.snowtouch.feature_new_advertisement.domain.model.UploadStatus
import kotlinx.coroutines.flow.Flow

interface NewAdRemoteRepository {

    suspend fun getNewAdId() : String?

    suspend fun postNewAdvertisement(advertisement : Advertisement) : Response<Boolean>

    fun uploadAdImages(adId : String, images: List<Uri>) : Flow<UploadStatus>
}