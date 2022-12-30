package com.cjmobileapps.stateflagandroid.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cjmobileapps.stateflagandroid.database.StateFlagDao
import com.cjmobileapps.stateflagandroid.network.StateFlagApi
import com.cjmobileapps.stateflagandroid.testutil.BaseTest
import com.cjmobileapps.stateflagandroid.testutil.TestCoroutineRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class StateFlagServiceTest : BaseTest() {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var mockStateFlagApi: StateFlagApi

    @Mock
    lateinit var mockStateFlagDao: StateFlagDao

    private lateinit var service: StateFlagServiceImpl

    @Before
    fun setup() {
        service = StateFlagServiceImpl(
            stateFlagApi = mockStateFlagApi,
            stateFlagDao = mockStateFlagDao
        )
    }

    @Test
    fun `get getAllStateFlags() success`(): Unit = runBlocking{

        // given
        val stateFlagDataList = FakeData.stateFlagDataList()

        // when
        Mockito.`when`(mockStateFlagDao.getAllStateFlags()).thenReturn(stateFlagDataList)

        // then
        val results = service.getAllStateFlagsFromDB()

        // verify
        Assert.assertEquals(
            stateFlagDataList,
            results
        )
    }

    @Test
    fun `get getStateFlagsAsync() success`(): Unit = runBlocking{

        // given
        val stateFlagDataList = FakeData.stateFlagDataList()
        val stateFlagDataListDeferredResponse = FakeData.stateFlagDataListDeferredResponse()


        // when
        Mockito.`when`(mockStateFlagApi.getStateFlagsAsync()).thenReturn(stateFlagDataListDeferredResponse)

        // then
        val results = service.getStateFlagsAsync()

        // verify
        Mockito.verify(mockStateFlagDao).deleteAll()
        Mockito.verify(mockStateFlagDao).insertAll(stateFlagDataList)
        Assert.assertEquals(
            true,
            results
        )
    }
}
