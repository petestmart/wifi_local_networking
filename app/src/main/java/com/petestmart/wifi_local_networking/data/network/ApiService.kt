package com.petestmart.wifi_local_networking.data.network

import com.petestmart.wifi_local_networking.data.model.Device
import retrofit2.http.GET

interface ApiService {
    @GET("devices")
    suspend fun getDevices(): List<Device>
}