package com.petestmart.wifi_local_networking.ui.devices

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DeviceScreen(viewModel: DeviceViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is DeviceUiState.Loading -> Text(("Loading..."), modifier = Modifier.fillMaxSize().padding(48.dp))
        is DeviceUiState.Error -> Text(("Error: ${state.message}"), modifier = Modifier.fillMaxSize().padding(48.dp))
        is DeviceUiState.Success -> {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                items(state.devices) { device ->
                    Text("${device.name} — ${device.status}",
                        modifier = Modifier.padding(vertical = 8.dp))
                }
            }
        }
    }
}