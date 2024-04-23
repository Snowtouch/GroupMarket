package com.snowtouch.feature_advertisement_details.data.repository

import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.repository.DatabaseReferenceManager
import com.snowtouch.feature_advertisement_details.domain.repository.AdDetailsRepository
import kotlinx.coroutines.tasks.await

class AdDetailsRepositoryImpl(
    private val dbReferences : DatabaseReferenceManager,
) : AdDetailsRepository {

    override suspend fun getAdDetails(adId : String) : Result<Advertisement> {
        return try {
            val advertisementDataSnapshot = dbReferences.advertisements.child(adId)
                .get()
                .await()

            val adData = advertisementDataSnapshot.getValue(Advertisement::class.java)
            if (adData != null)
                Result.Success(adData)
            else
                return Result.Failure(Exception())
        } catch (e : Exception) {
            return Result.Failure(e)
        }
    }
}