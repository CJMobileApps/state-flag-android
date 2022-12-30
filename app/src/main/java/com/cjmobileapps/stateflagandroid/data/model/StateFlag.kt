package com.cjmobileapps.stateflagandroid.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class StateFlagData(
    @PrimaryKey val title: String,
    val thumbnail: String,
    val url: String,
    val description: String
): Parcelable
