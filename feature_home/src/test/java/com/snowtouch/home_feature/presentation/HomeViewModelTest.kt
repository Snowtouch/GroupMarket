package com.snowtouch.home_feature.presentation

import com.snowtouch.core.domain.use_case.GetAdDetailsUseCase
import com.snowtouch.core.domain.use_case.GetUserFavoriteAdsIdsFlowUseCase
import com.snowtouch.core.domain.use_case.ToggleFavoriteAdUseCase
import com.snowtouch.core.domain.use_case.UpdateRecentlyViewedAdsListUseCase
import com.snowtouch.core.presentation.util.SnackbarGlobalDelegate
import com.snowtouch.home_feature.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    val mockHomeRepository = mock(HomeRepository::class.java)
    val mockGetAdDetailsUseCase = mock(GetAdDetailsUseCase::class.java)
    val mockGetUserFavoriteAdsIdsFlowUseCase = mock(GetUserFavoriteAdsIdsFlowUseCase::class.java)
    val mockToggleFavoriteAdUseCase = mock(ToggleFavoriteAdUseCase::class.java)
    val mockUpdateRecentlyViewedListUseCase = mock(UpdateRecentlyViewedAdsListUseCase::class.java)
    val mockSnackbar = mock(SnackbarGlobalDelegate::class.java)

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
    }

    @Test
    fun toggleFavoriteAd() = runTest {
        val newAdId = "123"
        viewModel.toggleFavoriteAd(newAdId)

    }

    @Test
    fun updateRecentlyViewedList() {
    }

    @Test
    fun updateSelectedAdId() = runTest {
        val newAdId = "123"
        viewModel.updateSelectedAdId(newAdId)
        assertEquals(newAdId, viewModel.adDetailsUiState.value.selectedAdId)
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