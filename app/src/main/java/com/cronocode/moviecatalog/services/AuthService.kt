package com.cronocode.moviecatalog.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

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

//    @POST("login")
//    @FormUrlEncoded
//    fun loginUser(
//        @Field("email") email: String,
//        @Field("password") password: String
//    ): Observable<String>
}