package com.cjmobileapps.stateflagandroid.data

import com.cjmobileapps.stateflagandroid.data.model.StateFlagData

interface StateFlagService {

    suspend fun getStateFlagsAsync(): Boolean

    suspend fun getAllStateFlagsFromDB(): List<StateFlagData>

}
