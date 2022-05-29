package com.cronocode.moviecatalog.services

import com.cronocode.moviecatalog.models.Movie
import com.cronocode.moviecatalog.models.MovieResponse
import com.cronocode.moviecatalog.models.MovieVideos
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

        @GET("/3/movie/{movieId}/videos")
        fun getMovieVideosById(
            @Path("movieId")movieId:String,
            @Query("api_key")api:String
        ): Call<MovieVideos>

        @GET("/3/search/movie")
        fun searchMovieApi(
            @Query("query") query: String,
            @Query("api_key") api:String
        ) :Call<MovieResponse>

        @GET("/3/movie/{movieId}")
        fun getCompanyOrGenreByMovieId(
            @Path("movieId")movieId:String,
            @Query("api_key")api:String
        ): Call<Movie>

    @GET("/3/discover/movie")
    fun getMovieListByGenres(
        @Query("api_key")api:String,
        @Query("with_genres")genres:String
    ): Call<MovieResponse>

//  @POST("register")
//        fun registerUser(
//            @Body request: RegisterRequest
//        ):Call<UserResponse>

}