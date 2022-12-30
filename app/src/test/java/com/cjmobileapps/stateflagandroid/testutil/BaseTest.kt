package com.cjmobileapps.stateflagandroid.testutil

import org.junit.After
import org.junit.Before
import org.mockito.MockitoAnnotations

abstract class BaseTest {

    private var closeable: AutoCloseable? = null

    @Before
    fun openMocks() {
        closeable = MockitoAnnotations.openMocks(this)
    }

    @After
    @Throws(Exception::class)
    fun releaseMocks() {
        closeable?.close()
    }
}
