package com.petestmart.wifi_local_networking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.petestmart.wifi_local_networking.ui.bluetooth.BleScreen
import com.petestmart.wifi_local_networking.ui.theme.Wifi_local_networkingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Wifi_local_networkingTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    BleScreen()
                }
            }
        }
    }
}