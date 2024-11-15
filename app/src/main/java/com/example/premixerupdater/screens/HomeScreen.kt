package com.example.premixerupdater.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.premixerupdater.R
import com.example.premixerupdater.Screen
import com.example.premixerupdater.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current

    // TODO: Check Device is Connected
    val isConnected = true

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(R.string.home))
                },
                actions = {
                    IconButton(
                        onClick = {
                            if (isConnected) {
                                navController.navigate(Screen.CreateRecipe.route)
                            } else {
                                Toast.makeText(
                                    context,
                                    R.string.check_device_toast,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = stringResource(R.string.create_recipe)
                        )
                    }

                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (isConnected) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        stringResource(R.string.current_recipe),
                        style = Typography.headlineLarge
                    )
                    // TODO: Current Recipe Card
                    Text(
                        stringResource(R.string.history),
                        style = Typography.headlineLarge
                    )
                    // TODO: List of History Recipe
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        stringResource(R.string.device_not_connected),
                        style = Typography.headlineLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = {
                        // TODO: GO SETTINGS
                    }) {
                        Text(
                            stringResource(R.string.go_settings),
                            style = Typography.labelLarge
                        )
                    }
                }
            }
        }
    }
}
