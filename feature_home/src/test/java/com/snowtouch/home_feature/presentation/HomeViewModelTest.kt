package com.snowtouch.home_feature.presentation

import com.google.common.truth.Truth.assertThat
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.AdvertisementPreview
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
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val mockHomeRepository = mock(HomeRepository::class.java)
    private val mockGetAdDetailsUseCase = mock(GetAdDetailsUseCase::class.java)
    private val mockGetUserFavoriteAdsIdsFlowUseCase =
        mock(GetUserFavoriteAdsIdsFlowUseCase::class.java)
    private val mockToggleFavoriteAdUseCase = mock(ToggleFavoriteAdUseCase::class.java)
    private val mockUpdateRecentlyViewedListUseCase =
        mock(UpdateRecentlyViewedAdsListUseCase::class.java)
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
        val snackbarMessage = "Error"
        val snackbarState = SnackbarState.ERROR

        `when`(mockToggleFavoriteAdUseCase.invoke(newAdId)).thenReturn(Result.Failure(Exception("Error")))
        doNothing().`when`(mockSnackbar).showSnackbar(
            snackbarState,
            snackbarMessage,
            withDismissAction = false
        )
        viewModel.toggleFavoriteAd(newAdId)

        verify(mockSnackbar).showSnackbar(snackbarState, snackbarMessage, withDismissAction = false)
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
    fun `getFavoriteAdvertisementsIds on Success updates favoriteAdsIds and sets UiState to Success`() =
        runTest {
            val favoriteAdsIds = listOf("1", "2", "3")

            `when`(mockGetUserFavoriteAdsIdsFlowUseCase.invoke())
                .thenReturn(flowOf(Result.Success(favoriteAdsIds)))
            viewModel.getFavoriteAdvertisementsIds()

            assertThat(viewModel.homeUiState.value.favoritesIdsList).isEqualTo(favoriteAdsIds)
            assertThat(viewModel.homeUiState.value.uiState).isEqualTo(UiState.Success)
        }

    @Test
    fun `getFavoriteAdvertisementsIds on Failure sets UiState to Error with message`() = runTest {
        val errorMessage = Exception("Error")
        `when`(mockGetUserFavoriteAdsIdsFlowUseCase.invoke())
            .thenReturn(flowOf(Result.Failure(errorMessage)))
        viewModel.getFavoriteAdvertisementsIds()

        assertThat(viewModel.homeUiState.value.uiState).isEqualTo(UiState.Error(errorMessage))
    }

    @Test
    fun `getFavoriteAdvertisementsIds() on Loading sets UiState to Loading`() {
        `when`(mockGetUserFavoriteAdsIdsFlowUseCase.invoke())
            .thenReturn(flowOf(Result.Loading))
        viewModel.getFavoriteAdvertisementsIds()

        assertThat(viewModel.homeUiState.value.uiState).isEqualTo(UiState.Loading)
    }

    @Test
    fun `getAdvertisementDetails() on start sets adDetailsUiState to Loading`() {
        val adId = "123"
        viewModel.getAdvertisementDetails(adId)
        assertThat(viewModel.adDetailsUiState.value.uiState).isEqualTo(UiState.Loading)
    }

    @Test
    fun `getAdvertisementDetails() on Success updates adDetailsUiState with adDetails and sets UiState to Success`() =
        runTest {
            val adId = "123"
            val adDetails = Advertisement(uid = "123", title = "Title", description = "Description")

            `when`(mockGetAdDetailsUseCase.invoke(adId)).thenReturn(Result.Success(adDetails))
            viewModel.getAdvertisementDetails(adId)

            assertThat(viewModel.adDetailsUiState.value.adDetails).isEqualTo(adDetails)
        }

    @Test
    fun `getAdvertisementDetails() on Failure sets adDetailsUiState to Error with message`() =
        runTest {
            val adId = "123"
            val errorMessage = Exception("Error")

            `when`(mockGetAdDetailsUseCase.invoke(adId)).thenReturn(Result.Failure(errorMessage))
            viewModel.getAdvertisementDetails(adId)

            assertThat(viewModel.adDetailsUiState.value.uiState).isEqualTo(
                UiState.Error(errorMessage)
            )
        }

    @Test
    fun `getNewAdvertisements() on start sets homeUiState to Loading`() {
        `when`(mockHomeRepository.getLatestAdsPreview())
            .thenReturn(flowOf(Result.Loading))

        viewModel.getNewAdvertisements()
        assertThat(viewModel.homeUiState.value.uiState).isEqualTo(UiState.Loading)
    }

    @Test
    fun `getNewAdvertisements() on Success updates homeUiState with newAdvertisements and sets UiState to Success`() =
        runTest {
            val newAdvertisements = listOf(AdvertisementPreview(uid = "123", title = "Title"))

            `when`(mockHomeRepository.getLatestAdsPreview())
                .thenReturn(flowOf(Result.Success(newAdvertisements)))
            viewModel.getNewAdvertisements()

            assertThat(viewModel.homeUiState.value.uiState).isEqualTo(UiState.Success)
            assertThat(viewModel.homeUiState.value.newAdsList).isEqualTo(newAdvertisements)
        }

    @Test
    fun `getNewAdvertisements() on Failure sets homeUiState to Error with message`() = runTest {
        val errorMessage = Exception("Error")

        `when`(mockHomeRepository.getLatestAdsPreview())
            .thenReturn(flowOf(Result.Failure(errorMessage)))
        viewModel.getNewAdvertisements()

        assertThat(viewModel.homeUiState.value.uiState).isEqualTo(UiState.Error(errorMessage))
    }

    @Test
    fun `getFavoriteAdvertisements() on start sets homeUiState to Loading`() {
        val favoriteAdsIds = listOf("1", "2", "3")
        `when`(mockHomeRepository.getUserFavoriteAdsPreview(favoriteAdsIds))
            .thenReturn(flowOf(Result.Loading))

        viewModel.getFavoriteAdvertisements(favoriteAdsIds)
        assertThat(viewModel.homeUiState.value.uiState).isEqualTo(UiState.Loading)
    }

    @Test
    fun `getFavoriteAdvertisements() on Success updates homeUiState with favoriteAdvertisements and sets UiState to Success`() =
        runTest {
            val favoriteAdsIds = listOf("1", "2", "3")
            val favoriteAdvertisements = listOf(
                AdvertisementPreview(uid = "1", title = "Title"),
                AdvertisementPreview(uid = "2", title = "Title"),
                AdvertisementPreview(uid = "3", title = "Title")
            )

            `when`(mockHomeRepository.getUserFavoriteAdsPreview(favoriteAdsIds))
                .thenReturn(flowOf(Result.Success(favoriteAdvertisements)))
            viewModel.getFavoriteAdvertisements(favoriteAdsIds)

            assertThat(viewModel.homeUiState.value.uiState).isEqualTo(UiState.Success)
            assertThat(viewModel.homeUiState.value.favoriteAdsList).isEqualTo(favoriteAdvertisements)
        }

    @Test
    fun `getRecentlyViewedAdvertisements() on start sets homeUiState to Loading`() {
        `when`(mockHomeRepository.getRecentlyViewedAdsPreview())
            .thenReturn(flowOf(Result.Loading))

        viewModel.getRecentlyViewedAdvertisements()
        assertThat(viewModel.homeUiState.value.uiState).isEqualTo(UiState.Loading)
    }

    @Test
    fun `getRecentlyViewedAdvertisements() on Success updates homeUiState with recentlyViewedAdvertisements and sets UiState to Success`() =
        runTest {
            val recentlyViewedAds = listOf(
                AdvertisementPreview(uid = "1", title = "Title"),
                AdvertisementPreview(uid = "2", title = "Title"),
                AdvertisementPreview(uid = "3", title = "Title")
            )

            `when`(mockHomeRepository.getRecentlyViewedAdsPreview())
                .thenReturn(flowOf(Result.Success(recentlyViewedAds)))
            viewModel.getRecentlyViewedAdvertisements()

            assertThat(viewModel.homeUiState.value.uiState).isEqualTo(UiState.Success)
            assertThat(viewModel.homeUiState.value.recentlyViewedList).isEqualTo(recentlyViewedAds)
        }

    @Test
    fun `getRecentlyViewedAdvertisements() on Failure sets homeUiState to Error with message`() =
        runTest {
            val errorMessage = Exception("Error")

            `when`(mockHomeRepository.getRecentlyViewedAdsPreview())
                .thenReturn(flowOf(Result.Failure(errorMessage)))
            viewModel.getRecentlyViewedAdvertisements()

            assertThat(viewModel.homeUiState.value.uiState).isEqualTo(UiState.Error(errorMessage))
        }
}