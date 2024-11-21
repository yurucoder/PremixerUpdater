package com.example.premixerupdater

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object CreateRecipe : Screen("create_recipe")
//    object WatchRecipe : Screen("detail/{id}") {
//        fun createRoute(id: Int) = "detail/$id"
//    }
}