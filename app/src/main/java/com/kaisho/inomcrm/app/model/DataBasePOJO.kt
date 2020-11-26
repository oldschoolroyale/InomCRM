package com.kaisho.inomcrm.app.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "database_table")
@Parcelize
data class DataBasePOJO(
    @PrimaryKey(autoGenerate = true)
    var positionId: Int,
    var id: String? = null,
    var name : String? = null,
    var address : String? = null,
    var status : String? = null,
    var phone : String? = null,
    var specialization: String? = null,
    var category: String? = null,
    var state: String? = null,
    var employee: String? = null,
    var owner: String? = null,
    var isSelected: Int = 0,
    var time: Int = 0,
    var textTown: String? = null,
    var textRegion: String? = null,
    var textType: String? = null
): Parcelable
