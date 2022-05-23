//package com.cronocode.moviecatalog.services
//
//import retrofit2.Retrofit
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.converter.scalars.ScalarsConverterFactory
//
//object RetrofitClient {
//    private var instance: Retrofit?=null
//    fun getInstance():Retrofit{
//
//        if(instance==null)
//            instance=Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:5000/api/auth/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//            return instance!!
//
//    }
//}