package com.cjmobileapps.stateflagandroid.hilt.module

import com.cjmobileapps.stateflagandroid.data.StateFlagService
import com.cjmobileapps.stateflagandroid.data.StateFlagServiceImpl
import com.cjmobileapps.stateflagandroid.database.StateFlagDao
import com.cjmobileapps.stateflagandroid.network.StateFlagApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun stateFlagService(
        stateFlagApi: StateFlagApi,
        stateFlagDao: StateFlagDao
    ) : StateFlagService {
        return StateFlagServiceImpl(stateFlagApi, stateFlagDao)
    }
}
