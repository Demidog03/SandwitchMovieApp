package com.cronocode.moviecatalog.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Genre (
    val id: String,
    val name: String
): Parcelable {
    constructor():this("", "")
}