package com.snowtouch.feature_advertisement_details.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.repository.DatabaseReferenceManager
import com.snowtouch.feature_advertisement_details.domain.repository.AdDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AdDetailsRepositoryImpl(
    private val auth : FirebaseAuth,
    private val dbReferences : DatabaseReferenceManager,
    private val dispatcher : CoroutineDispatcher,
) : AdDetailsRepository {

    override suspend fun getAdDetails(adId : String) : Result<Advertisement> {
        return withContext(dispatcher) {
           try  {
                val advertisementDataSnapshot = dbReferences.advertisements.child(adId)
                    .get()
                    .await()

                val adData = advertisementDataSnapshot.getValue(Advertisement::class.java)
                if (adData != null)
                    Result.Success(adData)
               else
                   return@withContext Result.Failure(Exception())
            } catch (e : Exception) {
                Result.Failure(e)
            }
        }
    }
}