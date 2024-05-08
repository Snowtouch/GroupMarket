package com.snowtouch.home_feature.presentation

import androidx.lifecycle.viewModelScope
import com.google.common.truth.Truth.assertThat
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.use_case.GetAdDetailsUseCase
import com.snowtouch.core.domain.use_case.GetUserFavoriteAdsIdsFlowUseCase
import com.snowtouch.core.domain.use_case.ToggleFavoriteAdUseCase
import com.snowtouch.core.domain.use_case.UpdateRecentlyViewedAdsListUseCase
import com.snowtouch.core.presentation.util.SnackbarGlobalDelegate
import com.snowtouch.core.presentation.util.SnackbarState
import com.snowtouch.home_feature.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val mockHomeRepository = mock(HomeRepository::class.java)
    private val mockGetAdDetailsUseCase = mock(GetAdDetailsUseCase::class.java)
    private val mockGetUserFavoriteAdsIdsFlowUseCase = mock(GetUserFavoriteAdsIdsFlowUseCase::class.java)
    private val mockToggleFavoriteAdUseCase = mock(ToggleFavoriteAdUseCase::class.java)
    private val mockUpdateRecentlyViewedListUseCase = mock(UpdateRecentlyViewedAdsListUseCase::class.java)
    private val mockSnackbar = mock(SnackbarGlobalDelegate::class.java)

    private lateinit var viewModel : HomeViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = HomeViewModel(
            mockHomeRepository,
            mockGetAdDetailsUseCase,
            mockGetUserFavoriteAdsIdsFlowUseCase,
            mockToggleFavoriteAdUseCase,
            mockUpdateRecentlyViewedListUseCase,
            mockSnackbar
        )
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        Mockito.clearAllCaches()
    }

    @Test
    fun `toggleFavoriteAd shows snackbar when error`() = runTest {
        val newAdId = "123"
        `when`(mockToggleFavoriteAdUseCase.invoke(newAdId)).thenReturn(Result.Failure(Exception("Error")))
        viewModel.toggleFavoriteAd(newAdId)

        verify(mockSnackbar).showSnackbar(SnackbarState.ERROR, message = "Error", withDismissAction = false)
    }

    @Test
    fun updateRecentlyViewedList() {
    }

    @Test
    fun `updateSelectedAdId updates uiStates selectedAdId`() = runTest {
        val newAdId = "123"
        viewModel.updateSelectedAdId(newAdId)
        assertThat(viewModel.adDetailsUiState.value.selectedAdId).isEqualTo(newAdId)
    }

    @Test
    fun `getFavoriteAdvertisementsIds on Success updates favoriteAdsIds and sets UiState to Success`() = runTest {
        val favoriteAdsIds = listOf("1", "2", "3")

        `when`(mockGetUserFavoriteAdsIdsFlowUseCase.invoke(viewModel.viewModelScope))
            .thenReturn(flowOf(Result.Success(favoriteAdsIds)))
        viewModel.getFavoriteAdvertisementsIds()

        assertThat(viewModel.homeUiState.value.favoritesIdsList).isEqualTo(favoriteAdsIds)
        assertThat(viewModel.homeUiState.value.uiState).isEqualTo(UiState.Success)
    }

    @Test
    fun `getFavoriteAdvertisementsIds on Failure sets UiState to Error with message`() = runTest {
        val errorMessage = Exception("Error")
        `when`(mockGetUserFavoriteAdsIdsFlowUseCase.invoke(viewModel.viewModelScope))
            .thenReturn(flowOf(Result.Failure(errorMessage)))
        viewModel.getFavoriteAdvertisementsIds()

        assertThat(viewModel.homeUiState.value.uiState).isEqualTo(UiState.Error(errorMessage))
    }

    @Test
    fun `getFavoriteAdvertisementsIds on Loading sets UiState to Loading`() {
        `when`(mockGetUserFavoriteAdsIdsFlowUseCase.invoke(viewModel.viewModelScope))
            .thenReturn(flowOf(Result.Loading))
        viewModel.getFavoriteAdvertisementsIds()

        assertThat(viewModel.homeUiState.value.uiState).isEqualTo(UiState.Loading)
    }

    @Test
    fun getAdvertisementDetails() {
    }


    @Test
    fun getNewAdvertisements() {
    }

    @Test
    fun getFavoriteAdvertisements() {
    }

    @Test
    fun getRecentlyViewedAdvertisements() {
    }
}