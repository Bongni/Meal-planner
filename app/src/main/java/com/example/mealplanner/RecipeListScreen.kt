package com.example.mealplanner

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun RecipeListScreen(navController: NavController, recipes: ArrayList<Recipe>, ingredients: ArrayList<Ingredient>) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(recipes.size) {index ->
                val recipe = recipes[index]

                RecipeItem(
                    recipe = recipe,
                    onRemove = {recipe -> recipes.remove(recipe)},
                    onAdd = {recipe -> addIngredients(ingredients, recipe)},
                    navController = navController
                )
            }
        }

        Button(
            onClick = {
                navController.navigate(Screen.AddScreen.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Text("add")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipeItem (
    recipe: Recipe,
    onRemove: (Recipe) -> Boolean,
    onAdd: (Recipe) -> Boolean,
    navController: NavController
) {
    val context = LocalContext.current
    var show by remember { mutableStateOf(true) }
    var deleted by remember { mutableStateOf(false) }
    var added by remember { mutableStateOf(false) }
    val currentItem by rememberUpdatedState(recipe)

    var dismissState = rememberDismissState(
        confirmStateChange = {
            if(it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                deleted = it == DismissValue.DismissedToEnd
                show = !deleted
                added = it == DismissValue.DismissedToStart

                true
            } else false
        }
    )

    AnimatedVisibility(
        visible = show,
        exit = fadeOut(spring())
    ) {
        SwipeToDismiss(
            state = dismissState,
            background = {
                DismissBackground(dismissState = dismissState)
            },
            dismissContent = {
                RecipeField(recipe = recipe)
            },
            modifier = Modifier.clickable {
                navController.navigate(Screen.RecipeScreen.withArgs(recipe.name))
            }
        )
    }

    LaunchedEffect(deleted || added) {
        if(deleted) {
            delay(800)
            onRemove(currentItem)
            Toast.makeText(context, "Recipe removed", Toast.LENGTH_SHORT).show()
        }

        if(added) {
            delay(800)
            onAdd(currentItem)
            Toast.makeText(context, "Recipe added to shoppinglist", Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeField (recipe : Recipe) {
    ListItem(
        headlineText = {
                       Text(recipe.name, style = MaterialTheme.typography.titleMedium)
        },
        modifier = Modifier.clip(MaterialTheme.shapes.small)
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DismissBackground (dismissState: DismissState) {
    val direction = dismissState.dismissDirection
    val color = when (direction) {
        DismissDirection.StartToEnd -> Color(0xFFFF1744)
        DismissDirection.EndToStart -> Color(0xFF1DE9B6)
        null -> Color.Transparent
    }


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp)
    ) {
        if(direction == DismissDirection.StartToEnd) Icon(
            Icons.Default.Delete,
            contentDescription = "delete"
        )
        Spacer(modifier = Modifier)
        if(direction == DismissDirection.EndToStart) Icon(
            Icons.Default.Add,
            contentDescription = "add"
        )
    }
}

fun addIngredients(ingredients: ArrayList<Ingredient>, recipe: Recipe): Boolean {
    for(ingredient in recipe.ingredients) {
        addIngredient(ingredients, ingredient)
    }

    return true
}

fun addIngredient(ingredients: ArrayList<Ingredient>, ingredient: Ingredient) {
    if (isIn (ingredients, ingredient)) {
        for (i in ingredients) {
            if (i.name == ingredient.name) {
                i.amount += ingredient.amount
            }
        }
    } else {
        ingredients.add(Ingredient(ingredient.name, ingredient.amount, ingredient.unit))
    }
}

fun isIn(ingredients: ArrayList<Ingredient>, ingredient: Ingredient): Boolean {
    for (i in ingredients) {
        if(ingredient.name == i.name) return true
    }

    return false
}
