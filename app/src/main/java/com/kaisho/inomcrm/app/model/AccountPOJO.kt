package com.kaisho.inomcrm.app.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "account_table")
@Parcelize
data class AccountPOJO(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var bonus: Long? = null,
    var image: String? = null,
    var manager: String? = null,
    var medications: String? = null,
    var medName: String? = null,
    var name: String? = null,
    var region: String? = null,
    var token: String? = null,
    var town: String? = null,
    var town_doctor: String? = null,
    var town_pharmacy: String? = null,
    var version: String? = null,
    var workerName: String? = null
):Parcelable