package com.cjmobileapps.stateflagandroid.hilt.module

import com.cjmobileapps.stateflagandroid.util.CoroutineDispatchers
import com.cjmobileapps.stateflagandroid.util.CoroutineDispatchersImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class CoroutinesModule {

    @Singleton
    @Provides
    fun coroutinesDispatchers() : CoroutineDispatchers {
        return CoroutineDispatchersImpl
    }
}
