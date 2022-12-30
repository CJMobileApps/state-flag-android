package com.cjmobileapps.stateflagandroid.testutil

import com.cjmobileapps.stateflagandroid.util.CoroutineDispatchers
import kotlinx.coroutines.Dispatchers

object TestCoroutineDispatchers : CoroutineDispatchers {
    override val io = Dispatchers.Unconfined

    override val main = Dispatchers.Unconfined
}
