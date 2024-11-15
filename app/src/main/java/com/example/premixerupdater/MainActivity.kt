package com.example.premixerupdater

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.premixerupdater.screens.CreateRecipeScreen
import com.example.premixerupdater.screens.HomeScreen
import com.example.premixerupdater.ui.theme.PremixerUpdaterTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val bluetoothManager: BluetoothManager = getSystemService(BluetoothManager::class.java)
            val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.getAdapter()
            if (bluetoothAdapter == null) {
                Toast.makeText(
                    LocalContext.current, R.string.device_not_support, Toast.LENGTH_SHORT
                ).show()
            }

            if (bluetoothAdapter?.isEnabled == false) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivity(enableBtIntent)
            }

            PremixerUpdaterTheme {
                MainScreen()
            }
        }
    }
}

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object CreateRecipe : Screen("create_recipe")
//    object WatchRecipe : Screen("detail/{id}") {
//        fun createRoute(id: Int) = "detail/$id"
//    }
}

@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController, startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(Screen.CreateRecipe.route) {
            CreateRecipeScreen(navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    PremixerUpdaterTheme {
        MainScreen()
    }
}
