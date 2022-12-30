package com.cjmobileapps.stateflagandroid.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cjmobileapps.stateflagandroid.data.model.StateFlagData

@Dao
interface StateFlagDao {

    @Query("SELECT * FROM stateFlagData")
    suspend fun getAllStateFlags(): List<StateFlagData>

    @Insert
    fun insertAll(stateFlagData: List<StateFlagData>)

    @Query("DELETE FROM stateFlagData")
    fun deleteAll()
}
