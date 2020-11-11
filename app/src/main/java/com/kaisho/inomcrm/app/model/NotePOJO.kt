package com.kaisho.inomcrm.app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NotePOJO(
    var id: String? = null,
    var name: String? = null,
    var address: String? = null,
    var visit: String? = null,
    var type: String? = null,
    var comment: String? = null
): Parcelable {
}