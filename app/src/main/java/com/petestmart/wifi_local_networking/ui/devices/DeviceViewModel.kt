package com.petestmart.wifi_local_networking.ui.devices

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petestmart.wifi_local_networking.data.model.Device
import com.petestmart.wifi_local_networking.data.repository.DeviceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeviceViewModel(
    private val repository: DeviceRepository = DeviceRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<DeviceUiState>(DeviceUiState.Loading)
    val uiState: StateFlow<DeviceUiState> = _uiState

    init {
        loadDevices()
    }

    private fun loadDevices() {
        viewModelScope.launch {
            _uiState.value = DeviceUiState.Loading
            repository.getDevices()
                .onSuccess { _uiState.value = DeviceUiState.Success(it) }
                .onFailure { _uiState.value = DeviceUiState.Error(it.message ?: "Unknown error") }
        }
    }
}

sealed class DeviceUiState {
    object Loading : DeviceUiState()
    data class Success(val devices: List<Device>) : DeviceUiState()
    data class Error(val message: String) : DeviceUiState()
}