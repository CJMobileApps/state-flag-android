package com.cjmobileapps.stateflagandroid.data

import com.cjmobileapps.stateflagandroid.database.StateFlagDao
import com.cjmobileapps.stateflagandroid.network.StateFlagApi
import com.cjmobileapps.stateflagandroid.ui.list.StateFlagListViewModel
import com.cjmobileapps.stateflagandroid.util.onError
import com.cjmobileapps.stateflagandroid.util.onSuccess
import timber.log.Timber

class StateFlagServiceImpl(
    private val stateFlagApi: StateFlagApi,
    private val stateFlagDao: StateFlagDao
): StateFlagService {

    private val tag = StateFlagListViewModel::class.java.simpleName

    override suspend fun getStateFlagsAsync(): Boolean {
            stateFlagApi.getStateFlagsAsync()
                .await()
                .onSuccess { stateFlagDataList ->
                    stateFlagDao.deleteAll()
                    stateFlagDao.insertAll(stateFlagDataList)
                    return true
                }
                .onError {
                    Timber.tag(tag).e("getStateFlagsAsync() error occurred")
                    return false
                }
            return false
    }

    override suspend fun getAllStateFlagsFromDB() = stateFlagDao.getAllStateFlags()
}
