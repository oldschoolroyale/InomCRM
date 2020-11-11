package com.kaisho.inomcrm.app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ViewPagerPOJO(
    var image: String? = null,
    var title: String? = null
):Parcelable


