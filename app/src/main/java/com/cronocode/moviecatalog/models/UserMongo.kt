package com.cronocode.moviecatalog.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserMongo (
    private val email: String,
    private val password: String,
) : Parcelable {
    constructor() : this("", "")
}