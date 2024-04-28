package com.snowtouch.feature_groups.presentation

import androidx.lifecycle.viewModelScope
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.use_case.GetUserFavoriteAdsIdsFlowUseCase
import com.snowtouch.core.domain.use_case.ToggleFavoriteAdUseCase
import com.snowtouch.core.presentation.GroupMarketViewModel
import com.snowtouch.core.presentation.util.SnackbarState
import com.snowtouch.feature_groups.domain.repository.GroupsRepository
import com.snowtouch.feature_groups.presentation.groups.GroupAdsUiState
import com.snowtouch.feature_groups.presentation.groups.GroupsUiState
import com.snowtouch.feature_groups.presentation.groups.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class GroupsViewModel(
    private val groupsRepository : GroupsRepository,
    private val getUserFavoriteAdsIdsFlowUseCase : GetUserFavoriteAdsIdsFlowUseCase,
    private val toggleFavoriteAdUseCase : ToggleFavoriteAdUseCase,
) : GroupMarketViewModel() {

    private val _groupsUiState = MutableStateFlow(GroupsUiState(UiState.Loading))
    val groupsUiState = _groupsUiState.asStateFlow()

    private val _groupAdsUiState = MutableStateFlow(GroupAdsUiState(UiState.Success))
    val groupAdsUiState = _groupAdsUiState.asStateFlow()

    init {
        //getFavoritesIds()
        getUserGroupsData()
    }

    fun updateSelectedGroup(groupId : String) {
        _groupAdsUiState.update { it.copy(selectedGroup = groupId) }
    }

    fun getGroupAdvertisements(groupId : String) {
        launchCatching {
            groupsRepository.getGroupAdvertisements(groupId).collect { result ->
                when (result) {
                    is Result.Loading -> _groupAdsUiState.update {
                        it.copy(uiState = UiState.Loading)
                    }

                    is Result.Failure -> _groupAdsUiState.update {
                        it.copy(uiState = UiState.Error(result.e))
                    }

                    is Result.Success -> _groupAdsUiState.update {
                        it.copy(uiState = UiState.Success, groupAds = result.data ?: emptyList())
                    }
                }
            }
        }
    }

    fun toggleFavoriteAd(adId : String) {
        launchCatching {
            when (val result = toggleFavoriteAdUseCase.invoke(adId)) {
                is Result.Failure ->
                    result.e.localizedMessage?.let { showSnackbar(SnackbarState.ERROR, it) }


                is Result.Loading -> _groupAdsUiState.update {
                    it.copy(uiState = UiState.Loading)
                }

                is Result.Success ->
                    showSnackbar(SnackbarState.DEFAULT, "Advertisement successfully added")

            }
        }
    }

    fun getUserGroupsData() {
        launchCatching {
            groupsRepository.getUserGroupsPreviewData().collect { result ->
                when (result) {
                    is Result.Loading -> _groupsUiState.update {
                        it.copy(uiState = UiState.Loading)
                    }

                    is Result.Failure -> _groupsUiState.update {
                        it.copy(uiState = UiState.Error(result.e))
                    }

                    is Result.Success -> _groupsUiState.update {
                        it.copy(
                            uiState = UiState.Success,
                            userGroupsList = result.data ?: emptyList()
                        )
                    }
                }
            }
        }
    }

    private fun getFavoritesIds() {
        launchCatching {
            getUserFavoriteAdsIdsFlowUseCase.invoke(viewModelScope).collect { result ->
                when (result) {
                    is Result.Failure -> _groupAdsUiState.update {
                        it.copy(uiState = UiState.Error(result.e))
                    }
                    Result.Loading -> _groupAdsUiState.update {
                        it.copy(uiState = UiState.Loading)
                    }
                    is Result.Success -> _groupAdsUiState.update {
                        it.copy(
                            uiState = UiState.Success,
                            favoritesList = result.data ?: emptyList()
                        )
                    }
                }
            }
        }
    }
}
