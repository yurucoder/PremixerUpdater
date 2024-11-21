package com.example.premixerupdater

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.premixerupdater.screens.CreateRecipeScreen
import com.example.premixerupdater.screens.HomeScreen
import com.example.premixerupdater.ui.theme.PremixerUpdaterTheme

const val PERMISSION_REQUEST_CODE = 100

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
            val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

            // Check Device Support
            if (bluetoothAdapter == null) {
                Toast.makeText(
                    this, R.string.device_not_support, Toast.LENGTH_SHORT
                ).show()
                throw RuntimeException(stringResource(R.string.device_not_support))
            }

            // Check Bluetooth Permission
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                    PERMISSION_REQUEST_CODE
                )
            }

            val navController: NavHostController = rememberNavController()

            PremixerUpdaterTheme {
                NavHost(
                    navController = navController, startDestination = Screen.Home.route
                ) {
                    composable(Screen.Home.route) {
                        HomeScreen(navController, bluetoothAdapter)
                    }
                    composable(Screen.CreateRecipe.route) {
                        CreateRecipeScreen(navController)
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)

        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] != PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(
                        this, R.string.permission_not_granted, Toast.LENGTH_SHORT
                    ).show()
                    throw RuntimeException()
                }
            }
        }
    }
}