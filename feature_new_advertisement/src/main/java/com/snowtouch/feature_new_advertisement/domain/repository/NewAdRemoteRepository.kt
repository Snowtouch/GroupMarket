package com.snowtouch.feature_new_advertisement.domain.repository

import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Result
import com.snowtouch.feature_new_advertisement.domain.model.UploadStatus
import kotlinx.coroutines.flow.Flow

interface NewAdRemoteRepository {

    suspend fun getUserGroupsIdNamePairs() : Result<List<Map<String, String>>>

    suspend fun getNewAdId() : String?

    suspend fun postNewAdvertisement(advertisement : Advertisement) : Result<String>

    fun uploadAdImage(adId : String, imageName : String, imageBytes : ByteArray) : Flow<UploadStatus>
}