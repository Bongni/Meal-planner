package com.example.mealplanner

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ShoppingListScreen (ingredients: ArrayList<Ingredient>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            items(ingredients.size) {index ->
                val ingredient = ingredients[index]

                IngredientItem(
                    ingredient = ingredient,
                    onRemove = { ingredient ->
                        ingredients.remove(ingredient)

                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IngredientItem (
    ingredient: Ingredient,
    onRemove: (Ingredient) -> Boolean
) {
    val context = LocalContext.current
    var show by remember { mutableStateOf(true) }
    val currentItem by rememberUpdatedState(ingredient)

    var dismissState = rememberDismissState(
        confirmStateChange = {
            if(it == DismissValue.DismissedToEnd) {
                show = false

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
                DismissBackgroundDeleteOnly(dismissState = dismissState)
            },
            dismissContent = {
                Ingredient(ingredient = ingredient)
            },
            modifier = Modifier
                .fillMaxSize()
        )
    }

    LaunchedEffect(show) {
        if(!show) {
            delay(800)
            onRemove(currentItem)
            Toast.makeText(context, "Recipe removed", Toast.LENGTH_SHORT).show()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DismissBackgroundDeleteOnly (dismissState: DismissState) {
    val direction = dismissState.dismissDirection
    val color = when (direction) {
        DismissDirection.StartToEnd -> Color(0xFFFF1744)
        DismissDirection.EndToStart -> Color.Transparent
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
    }
}