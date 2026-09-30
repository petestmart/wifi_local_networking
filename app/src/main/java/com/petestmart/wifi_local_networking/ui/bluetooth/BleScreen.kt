package com.petestmart.wifi_local_networking.ui.bluetooth

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BleScreen(viewModel: BleViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.values.all { it }
        if (granted) {
            viewModel.startScan(context)
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(48.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                permissionLauncher.launch(arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT
                ))
            } else {
                viewModel.startScan(context)
            }
        }) {
            Text("Scan for BLE Devices")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = uiState) {
            is BleUiState.Idle -> Text(("Press scan to find nearby devices"), modifier = Modifier.fillMaxSize().padding(48.dp))
            is BleUiState.Scanning -> Text(("Scanning..."), modifier = Modifier.fillMaxSize().padding(48.dp))
            is BleUiState.Error -> Text(("Error: ${state.message}"), modifier = Modifier.fillMaxSize().padding(48.dp))
            is BleUiState.Results -> {
                Text("Found ${state.devices.size} devices", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn {
                    items(state.devices) { device ->
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Text(device.name, fontSize = 16.sp)
                            Text(device.address, fontSize = 12.sp)
                            Text("Signal: ${device.rssi} dBm", fontSize = 12.sp)
                        }
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}