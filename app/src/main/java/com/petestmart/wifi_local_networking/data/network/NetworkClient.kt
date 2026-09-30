package com.petestmart.wifi_local_networking.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {
    val api: ApiService = Retrofit.Builder()
        .baseUrl("http://192.168.68.103:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}