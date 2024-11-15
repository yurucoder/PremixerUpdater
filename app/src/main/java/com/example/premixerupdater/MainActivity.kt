package com.example.premixerupdater

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.premixerupdater.screens.CreateRecipeScreen
import com.example.premixerupdater.screens.HomeScreen
import com.example.premixerupdater.ui.theme.PremixerUpdaterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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
fun MainScreen() {
    val navController: NavController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
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
