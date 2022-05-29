package com.cronocode.moviecatalog.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id")
    val id : String ?,

    @SerializedName("title")
    val title : String?,

    @SerializedName("poster_path")
    val poster : String?,

    @SerializedName("release_date")
    val release : String?,

    @SerializedName("vote_average")
    val rating : String?,

    @SerializedName("overview")
    val overview : String?,

    @SerializedName("backdrop_path")
    val backdropUrl : String?,
    @SerializedName("genres")
    val genres : List<Genre>,

    @SerializedName("production_companies")
    val companies: List<Company>

) : Parcelable{
    constructor() : this("", "", "", "", "", "", "", mutableListOf(), mutableListOf())
}