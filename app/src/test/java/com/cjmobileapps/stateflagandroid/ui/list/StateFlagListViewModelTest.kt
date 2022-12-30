package com.cjmobileapps.stateflagandroid.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cjmobileapps.stateflagandroid.data.FakeData
import com.cjmobileapps.stateflagandroid.data.StateFlagService
import com.cjmobileapps.stateflagandroid.testutil.BaseTest
import com.cjmobileapps.stateflagandroid.testutil.TestCoroutineDispatchers
import com.cjmobileapps.stateflagandroid.testutil.TestCoroutineRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.internal.verification.Times

class StateFlagListViewModelTest : BaseTest() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var mockStateFlagService: StateFlagService

    private lateinit var viewModel: StateFlagListViewModel

    private fun setupViewModel() {
        viewModel = StateFlagListViewModel(
            stateFlagService = mockStateFlagService,
            dispatchers = TestCoroutineDispatchers
        )
    }

    @Test
    fun `get init getStateFlagDataList() success`() = runBlocking {

        // given
        val mockStateFlagDataList =
            StateFlagListViewModel.StateFlagListState.StateFlagListStateLoaded(
                stateFlagDataList = FakeData.stateFlagDataList()
                    .sortedBy { it.title }
            ).stateFlagDataList

        val stateFlagsAsyncSuccess = true

        // when
        Mockito.`when`(mockStateFlagService.getStateFlagsAsync()).thenReturn(stateFlagsAsyncSuccess)
        Mockito.`when`(mockStateFlagService.getAllStateFlagsFromDB())
            .thenReturn(FakeData.stateFlagDataList())

        // then
        setupViewModel()
        val state =
            viewModel.stateLiveData.value as StateFlagListViewModel.StateFlagListState.StateFlagListStateLoaded

        // verify
        Assert.assertEquals(
            mockStateFlagDataList,
            state.stateFlagDataList
        )
    }

    @Test
    fun `get call getStateFlagDataList() success`() = runBlocking {

        // given
        val mockStateFlagDataList =
            StateFlagListViewModel.StateFlagListState.StateFlagListStateLoaded(
                stateFlagDataList = FakeData.stateFlagDataList()
                    .sortedBy { it.title }
            ).stateFlagDataList

        val stateFlagsAsyncSuccess = true

        // when
        Mockito.`when`(mockStateFlagService.getStateFlagsAsync()).thenReturn(stateFlagsAsyncSuccess)
        Mockito.`when`(mockStateFlagService.getAllStateFlagsFromDB())
            .thenReturn(FakeData.stateFlagDataList())

        // then
        setupViewModel()
        viewModel.getStateFlagDataList()
        val state =
            viewModel.stateLiveData.value as StateFlagListViewModel.StateFlagListState.StateFlagListStateLoaded

        // verify
        Mockito.verify(mockStateFlagService, Times(2)).getStateFlagsAsync()
        Mockito.verify(mockStateFlagService, Times(2)).getAllStateFlagsFromDB()
        Assert.assertEquals(
            mockStateFlagDataList,
            state.stateFlagDataList
        )
    }

    @Test
    fun `get init getStateFlagDataList() throw errors`() = runBlocking {

        // given
        val mockErrorShown = true

        // when
        Mockito.`when`(mockStateFlagService.getStateFlagsAsync()).thenThrow(RuntimeException("Random Error occurred"))
        Mockito.`when`(mockStateFlagService.getAllStateFlagsFromDB()).thenReturn(emptyList())

        // then
        setupViewModel()
        val state = viewModel.stateLiveData.value as StateFlagListViewModel.StateFlagListState.StateFlagListStateLoaded

        // verify
        Assert.assertEquals(
            mockErrorShown,
            state.showError.value
        )
    }
}
