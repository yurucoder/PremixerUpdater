package com.example.premixerupdater.services

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import java.util.UUID

class BluetoothService(context: Context) {

    companion object {
        const val PERMISSION_REQUEST_CODE = 100
    }

    private var context: Context
    private var bt06Device: BluetoothDevice? = null
    private var bluetoothSocket: BluetoothSocket? = null

    var adapter: BluetoothAdapter
    var permissionIsAvailable = false
    var deviceIsConnected = false

    init {
        val bluetoothManager: BluetoothManager =
            context.getSystemService(BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

        // check device support bluetooth
        if (bluetoothAdapter == null) {
            throw RuntimeException("Device not supporting bluetooth")
        }

        this.adapter = bluetoothAdapter
        this.context = context
    }

    fun checkBluetoothPermission() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    fun connectDeviceAsSocket(name: String, uuid: UUID): BluetoothSocket {
        checkBluetoothPermission()
        bt06Device = adapter
            .bondedDevices
            ?.find { device -> device.name == name }
            ?: throw RuntimeException("Device not found")
        bluetoothSocket = bt06Device!!.createRfcommSocketToServiceRecord(uuid)
        bluetoothSocket?.connect()
            ?: throw RuntimeException("Failed to connect socket")
        deviceIsConnected = true
        return bluetoothSocket!!
    }

    fun getDataFromArduino() {
        Thread {
            val buffer = ByteArray(1024)
            while (true) {
                val bytes = bluetoothSocket?.inputStream?.read(buffer)
                if (bytes != null && bytes > 0) {
                    val received = String(buffer, 0, bytes)
                }
            }
        }.start()
    }

    fun sendDataToArduino() {

    }
}