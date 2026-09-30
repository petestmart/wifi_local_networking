package com.petestmart.wifi_local_networking.data.repository

import com.petestmart.wifi_local_networking.data.model.Device
import com.petestmart.wifi_local_networking.data.network.ApiService
import com.petestmart.wifi_local_networking.data.network.NetworkClient

class DeviceRepository(
    private val api: ApiService = NetworkClient.api
) {
    suspend fun getDevices(): Result<List<Device>> {
        return try {
            Result.success(api.getDevices())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}