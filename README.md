Working through each phase of this list

Phase 1 — WiFi & Local Networking
Scan the local network, discover devices (or a mock server you run on your laptop), display their status. Teaches you WifiManager, network permissions, and working with local HTTP or mDNS/Bonjour discovery. Real skill: understanding how Android interacts with the network layer beneath the API level.

Phase 2 — Bluetooth
Add a BLE (Bluetooth Low Energy) device — a real one like a Govee light, a cheap sensor from Amazon, or even another phone. Scan for devices, pair, read characteristics, send commands. BLE in Android is notoriously painful, which makes it a great thing to actually have wrestled with.

Phase 3 — Camera
Add a "room scanner" or QR/barcode mode — point the camera at a device and it pulls up that device's control panel. Uses CameraX, which is the current right way to do camera on Android, and gives you a reason to learn ML Kit for the code scanning.

Phase 4 — IoT Protocol
Add MQTT support — it's the standard lightweight protocol for IoT. Connect to a broker (Mosquitto runs locally, or use a free cloud one), subscribe to topics, publish commands. Now your app can talk to almost any hobbyist IoT device.

Phase 5 — Android Auto
Build a "driving mode" that surfaces a simplified view: current home status, one-tap controls for lights or a garage. Android Auto has a real approval process for production, but you can run it in the emulator with Desktop Head Unit (DHU) for development. This one alone is a differentiator on a resume — very few Android devs have touched it.
