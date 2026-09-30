package com.petestmart.wifi_local_networking.ui.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

data class BleDevice(
    val name: String,
    val address: String,
    val rssi: Int
)

sealed class BleUiState {
    object Idle : BleUiState()
    object Scanning : BleUiState()
    data class Results(val devices: List<BleDevice>) : BleUiState()
    data class Error(val message: String) : BleUiState()
}

class BleViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<BleUiState>(BleUiState.Idle)
    val uiState: StateFlow<BleUiState> = _uiState

    private val foundDevices = mutableMapOf<String, BleDevice>()

    private val scanCallback = object : ScanCallback() {
        @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val device = BleDevice(
                name = result.device.name ?: "Unknown",
                address = result.device.address,
                rssi = result.rssi
            )
            foundDevices[device.address] = device
            _uiState.value = BleUiState.Results(foundDevices.values.toList())
        }

        override fun onScanFailed(errorCode: Int) {
            _uiState.value = BleUiState.Error("Scan failed with code $errorCode")
        }
    }

    @SuppressLint("MissingPermission")
    fun startScan(context: Context) {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        val scanner = bluetoothManager.adapter?.bluetoothLeScanner

        if (scanner == null) {
            _uiState.value = BleUiState.Error("Bluetooth not available")
            return
        }

        foundDevices.clear()
        _uiState.value = BleUiState.Scanning

        val settings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build()

        scanner.startScan(null, settings, scanCallback)

        // Auto-stop after 10 seconds
        viewModelScope.launch {
            delay(10_000.milliseconds)
            scanner.stopScan(scanCallback)
        }
    }
}