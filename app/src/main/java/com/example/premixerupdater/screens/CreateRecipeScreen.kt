package com.example.premixerupdater.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.premixerupdater.R
import com.example.premixerupdater.Screen
import com.example.premixerupdater.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRecipeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.create_recipe),
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate(Screen.Home.route) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.go_home)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            // TODO: ADD BLUETOOTH MODULE
                            // TODO: ADD ROOM MODULE
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = stringResource(R.string.create_recipe)
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(
                stringResource(R.string.drink_slot),
                style = Typography.headlineLarge
            )
            // TODO: ADD SLOT FORM
            Text(
                stringResource(R.string.add_recipes),
                style = Typography.headlineLarge
            )
            // TODO: ADD RECIPE FORM
        }
    }
}
