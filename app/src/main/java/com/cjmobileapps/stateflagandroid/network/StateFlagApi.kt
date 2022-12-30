package com.cjmobileapps.stateflagandroid.network

import com.cjmobileapps.stateflagandroid.data.model.StateFlagData
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface StateFlagApi {

    @GET("v1/storages/63409834eced9b09e99ebdcd/data")
    fun getStateFlagsAsync(): Deferred<Response<List<StateFlagData>>>
}
