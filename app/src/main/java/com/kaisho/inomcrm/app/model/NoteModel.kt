package com.kaisho.inomcrm.app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NoteModel(
    var id: String? = null,
    var name: String? = null,
    var address: String? = null,
    var status: String? = null,
    var phone: String? = null,
    var specialization: String? = null,
    var category: String? = null,
    var state: String? = null,
    var employee: String? = null,
    var owner: String? = null,
    var visit: Int = 0,
    var time: Int = 0,
    var type: Int = 0,
    var viewType: Int = -1
):Parcelable