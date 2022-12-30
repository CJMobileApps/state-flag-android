package com.cjmobileapps.stateflagandroid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cjmobileapps.stateflagandroid.data.model.StateFlagData

@Database(entities = [StateFlagData::class], version = 1)
abstract class StateFlagDatabase: RoomDatabase() {
    abstract fun stateFlagDao(): StateFlagDao
}

class DatabaseFactory {

    companion object {

        fun getDB(context: Context): StateFlagDatabase {
            return Room.databaseBuilder(
                context,
                StateFlagDatabase::class.java, "stateflag-database"
            ).build()
        }
    }
}
