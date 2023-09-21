package com.example.mealplanner

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.example.mealplanner.ui.theme.MealPlannerTheme

sealed class NavItem (
    var title: String,
    var icon: ImageVector,
    var route: String
) {
    object ShoppingList : NavItem("Shopping List", Icons.Default.ShoppingCart, Screen.ShoppingListScreen.route)
    object RecipeList : NavItem("Recipe List", Icons.Default.List, Screen.RecipeListScreen.route)
}

@Composable
fun TopNavBar (navController: NavController) {

    val items = listOf(
        NavItem.ShoppingList,
        NavItem.RecipeList
    )

    NavigationBar {
        items.forEach { item ->
            AddItem(
                screen = item,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: NavItem,
    navController: NavController
) {
    NavigationBarItem(
        selected = screen.route == navController.currentBackStackEntry?.destination?.route!!,
        onClick = {
                  navController.navigate(screen.route)
        },
        label = {
                Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = screen.title,
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBarSmall (navController: NavController, title: String) {
    TopAppBar(
        title = { Text("$title") },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate(Screen.RecipeListScreen.route)
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(

        )
    )
}