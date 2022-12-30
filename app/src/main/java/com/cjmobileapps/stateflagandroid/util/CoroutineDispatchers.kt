package com.cjmobileapps.stateflagandroid.util

import kotlin.coroutines.CoroutineContext

interface CoroutineDispatchers {

    val io: CoroutineContext

    val main: CoroutineContext
}
