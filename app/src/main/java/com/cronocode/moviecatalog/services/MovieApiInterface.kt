package com.cronocode.moviecatalog.services

import com.cronocode.moviecatalog.models.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiInterface {

//    @GET("/3/movie/upcoming?api_key=bbf5a3000e95f1dddf266b5e187d4b21")
//    fun getMovieList(): Call<MovieResponse>
//
    @GET("/3/movie/{type}")
        fun getMovieList(
            @Path("type")type:String,
            @Query("api_key")api:String
        ): Call<MovieResponse>
}