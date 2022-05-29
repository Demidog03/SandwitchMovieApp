package com.cronocode.moviecatalog.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Company(
    @SerializedName("id")
    val id : String ?,

    @SerializedName("name")
    val name : String?,

    @SerializedName("logo_path")
    val logo : String?,

    @SerializedName("origin_country")
    val country : String?
): Parcelable {
    constructor() : this("", "", "", "")
}
