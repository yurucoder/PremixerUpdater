package com.example.premixerupdater

data class Recipe(
    val drinkA: String,
    val drinkB: String,
    val ratioA: Int,
    val ratioB: Int,
)

data class DrinkSheet(
    val id: Int,
    val drinks: List<String>,
    val recipes: List<Recipe>
)

val sampleDrinkSheet = DrinkSheet(
    id = 0,
    drinks = listOf("cola", "soda", "water", "sake"),
    recipes = listOf(
        Recipe(
            drinkA = "cola",
            drinkB = "soda",
            ratioA = 1,
            ratioB = 2,
        ),
        Recipe(
            drinkA = "cola",
            drinkB = "water",
            ratioA = 2,
            ratioB = 1,
        ),
    )
)