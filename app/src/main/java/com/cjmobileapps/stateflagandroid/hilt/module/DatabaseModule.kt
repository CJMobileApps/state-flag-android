package com.cjmobileapps.stateflagandroid.hilt.module

import android.content.Context
import com.cjmobileapps.stateflagandroid.database.DatabaseFactory
import com.cjmobileapps.stateflagandroid.database.StateFlagDao
import com.cjmobileapps.stateflagandroid.database.StateFlagDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun stateFlagDatabase(@ApplicationContext context: Context) : StateFlagDatabase {
        return DatabaseFactory.getDB(context)
    }

    @Singleton
    @Provides
    fun stateFlagDao(stateFlagDatabase: StateFlagDatabase): StateFlagDao {
        return stateFlagDatabase.stateFlagDao()
    }
}
