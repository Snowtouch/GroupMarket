package com.snowtouch.feature_groups.presentation

import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.repository.CoreRepository
import com.snowtouch.core.presentation.GroupMarketViewModel
import com.snowtouch.feature_groups.domain.repository.GroupsRepository
import com.snowtouch.feature_groups.presentation.group_ads.GroupAdsUiState
import com.snowtouch.feature_groups.presentation.groups.GroupsUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class GroupsViewModel(
    private val groupsRepository : GroupsRepository,
    private val coreRepository : CoreRepository,
): GroupMarketViewModel() {

    private val _uiState = MutableStateFlow<GroupsUiState>(GroupsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _groupAdsUiState = MutableStateFlow<GroupAdsUiState>(GroupAdsUiState.Loading)
    val groupAdsUiState = _groupAdsUiState.asStateFlow()

    val currentUserFavoriteAdsIds: Flow<List<String>> = coreRepository.currentUserFavoriteAdsIds

    init {
        getUserGroupsData()
    }

    fun getGroupAdvertisements(groupId: String) {
        launchCatching {
            groupsRepository.getGroupAdvertisements(groupId).collect { result ->
                when (result) {
                    is Result.Loading -> _groupAdsUiState.update { GroupAdsUiState.Loading }
                    is Result.Failure -> _groupAdsUiState.update { GroupAdsUiState.Error(result.e)}
                    is Result.Success -> _groupAdsUiState.update {
                        GroupAdsUiState.Success(result.data ?: emptyList())
                    }
                }
            }
        }
    }

    fun toggleFavoriteAd(adId : String) {
        launchCatching {
            coreRepository.toggleFavoriteAd(adId)
        }
    }

    fun getUserGroupsData() {
        launchCatching {
            groupsRepository.getUserGroupsPreviewData().collect { result ->
                when (result) {
                    is Result.Loading -> _uiState.update { GroupsUiState.Loading }
                    is Result.Failure -> _uiState.update { GroupsUiState.Error(result.e) }
                    is Result.Success -> _uiState.update {
                        GroupsUiState.Success(result.data ?: emptyList())
                    }
                }
            }
        }
    }
}
