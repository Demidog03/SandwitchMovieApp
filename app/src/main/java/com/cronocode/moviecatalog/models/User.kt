package com.cronocode.moviecatalog.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("uId")
    val uId : String ?,

    @SerializedName("name")
    val name : String?,

    @SerializedName("profile")
    val profile : String?,

    @SerializedName("city")
    val city : String?,


) : Parcelable{
    constructor() : this("", "", "", "")
}