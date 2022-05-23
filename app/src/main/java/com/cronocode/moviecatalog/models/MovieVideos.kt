package com.cronocode.moviecatalog.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieVideos (
    val id : String?,
    val results: List<MovieVideosResults>
): Parcelable {
    constructor() : this("", mutableListOf())
}