package com.petestmart.wifi_local_networking.ui.camera

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class CameraUiState {
    object Scanning : CameraUiState()
    data class DeviceFound(val deviceId: String, val rawValue: String) : CameraUiState()
    data class NotFound(val rawValue: String) : CameraUiState()
}

class CameraViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<CameraUiState>(CameraUiState.Scanning)
    val uiState: StateFlow<CameraUiState> = _uiState

    // Called when ML Kit successfully reads a barcode
    fun onBarcodeDetected(rawValue: String) {
        // For now, treat the QR code value as a device ID
        // and look it up against your known devices
        val knownDevices = mapOf(
            "device-1" to "1",
            "device-2" to "2",
            "device-3" to "3"
        )

        val deviceId = knownDevices[rawValue]
        if (deviceId != null) {
            _uiState.value = CameraUiState.DeviceFound(deviceId, rawValue)
        } else {
            _uiState.value = CameraUiState.NotFound(rawValue)
        }
    }

    fun resetScan() {
        _uiState.value = CameraUiState.Scanning
    }
}