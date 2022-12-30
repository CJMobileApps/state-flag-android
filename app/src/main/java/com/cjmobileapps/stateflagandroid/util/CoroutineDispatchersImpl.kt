package com.cjmobileapps.stateflagandroid.util

import kotlinx.coroutines.Dispatchers

object CoroutineDispatchersImpl : CoroutineDispatchers {
    override val io = Dispatchers.IO

    override val main = Dispatchers.Main
}
