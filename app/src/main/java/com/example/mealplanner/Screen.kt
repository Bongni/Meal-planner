package com.example.mealplanner

sealed class Screen(val route: String) {
    object ShoppingListScreen : Screen("shoppinglist_screen")
    object RecipeListScreen : Screen("recipelist_screen")
    object RecipeScreen : Screen("recipe_screen")
    object AddScreen : Screen("add_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {arg ->
                append("/$arg")
            }
        }
    }
}
