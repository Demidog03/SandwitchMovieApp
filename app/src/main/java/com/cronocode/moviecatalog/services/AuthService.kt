package com.cronocode.moviecatalog.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
//    @POST("register")
//    @FormUrlEncoded
//    fun registerUser(
//        @Field("email") email: String,
//        @Field("password") password: String
//    ) : Observable<String>

    @POST("register")
    fun registerUser(@Body body: Map<String, String>): Call<Any?>?
    @POST("login")
    fun loginUser(@Body body: Map<String, String>): Call<Any?>?
    @POST("getUserById")
    fun getUserById(@Body body: Map<String, String>): Call<Any?>?
    @POST("addToFavoriteMovies")
    fun addToFavoriteMovies(@Body body: Map<String, String>): Call<Any?>?
    @POST("removeFromFavoriteMovies")
    fun removeFromFavoriteMovies(@Body body: Map<String, String>): Call<Any?>?
    @GET("getFavorites")
    fun getFavourites(@Query("userId") userId: String): Call<Any?>?
    @GET("isFavoriteMovie")
    fun isFavoriteMovie(@Query("userId") userId: String,
                        @Query("movieId")movieId: String): Call<Any?>?
//    @POST("login")
//    @FormUrlEncoded
//    fun loginUser(
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): Observable<String>
}