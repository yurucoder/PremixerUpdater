package com.example.premixerupdater

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.premixerupdater.screens.CreateRecipeScreen
import com.example.premixerupdater.screens.HomeScreen
import com.example.premixerupdater.services.BluetoothService
import com.example.premixerupdater.ui.theme.PremixerUpdaterTheme
import java.io.IOException
import java.util.UUID

val BT06_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object CreateRecipe : Screen("create_recipe")
//    object WatchRecipe : Screen("detail/{id}") {
//        fun createRoute(id: Int) = "detail/$id"
//    }
}

class MainActivity : ComponentActivity() {

    lateinit var bluetoothService: BluetoothService
    lateinit var bluetoothAdapter: BluetoothAdapter
    lateinit var bluetoothSocket: BluetoothSocket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        try {
            bluetoothService = BluetoothService(this)
            bluetoothAdapter = bluetoothService.adapter
        } catch (e: RuntimeException) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }

        setContent {
            PremixerUpdaterTheme {
                AppNavigation(rememberNavController())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!bluetoothService.deviceIsConnected) {
            try {
                bluetoothSocket = bluetoothService.connectDeviceAsSocket("BT-06", BT06_UUID)
            } catch (e: RuntimeException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Composable
    fun AppNavigation(navController: NavHostController) {
        NavHost(
            navController = navController, startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController, bluetoothService)
            }
            composable(Screen.CreateRecipe.route) {
                CreateRecipeScreen(navController, bluetoothSocket)
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
            BluetoothService.PERMISSION_REQUEST_CODE -> {
                bluetoothService.permissionIsAvailable = grantResults.isNotEmpty() &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED
                return
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            bluetoothSocket.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}