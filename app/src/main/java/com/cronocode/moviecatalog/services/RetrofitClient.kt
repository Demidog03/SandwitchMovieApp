package com.cronocode.moviecatalog.services

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var instance: Retrofit?=null
    fun getInstance():Retrofit{

        if(instance==null)
            instance=Retrofit.Builder()
                .baseUrl("http://192.168.100.9:5000/api/auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return instance!!

    }
}