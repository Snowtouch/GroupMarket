package com.snowtouch.core.data.repository

import com.google.firebase.database.getValue
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.repository.CoreRepository
import com.snowtouch.core.domain.repository.DatabaseReferenceManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class CoreRepositoryImpl(
    private val dbReference : DatabaseReferenceManager,
    private val dispatcher : CoroutineDispatcher,
) : CoreRepository {
    override val currentUserFavoriteAdsIds : Flow<List<String>>
        get() = flow {
            val favAdsId = dbReference
                .currentUserFavoriteAdsIds
                .get()
                .await()
                .children
                .mapNotNull {
                    it.getValue<String>()
                }
            emit(favAdsId)
        }

    override suspend fun toggleFavoriteAd(adId : String) : Result<Boolean> {
        return withContext(dispatcher) {
            try {
                val favAdsIds = dbReference
                    .currentUserFavoriteAdsIds
                    .get()
                    .await()
                    .children
                    .mapNotNull {
                        it.getValue<String>()
                    }
                val updatedList = favAdsIds.toMutableList()

                if (favAdsIds.contains(adId))
                    updatedList.remove(adId)
                else updatedList.add(adId)

                Result.Success(true)
            } catch (e : Exception) {
                Result.Failure(e)
            }
        }
    }
}