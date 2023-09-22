package com.example.mealplanner

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen (navController: NavController, recipes: ArrayList<Recipe>) {
    var name by remember { mutableStateOf("") }
    var recipe by remember { mutableStateOf("") }
    var ingredients = remember { mutableStateListOf<Ingredient>() }

    Column (
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TextField(
            value = name,
            onValueChange = {
                name = it
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))

        TextField(
            value = recipe,
            onValueChange = {
                recipe = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        
        ingredients.forEach {ingredient ->
            Ingredient(ingredient = ingredient)
        }

        IngredientInput(ingredients = ingredients)

        Button (
            onClick = {
                val recipe = Recipe(name, recipe, ArrayList<Ingredient>(ingredients.toList()))

                recipes.add(recipe)
                navController.navigate(Screen.RecipeListScreen.route)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Done")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IngredientInput (ingredients: SnapshotStateList<Ingredient>) {
    Column {
        var ingredientName by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("") }
        var selectedUnit by remember { mutableStateOf(com.example.mealplanner.Unit.G) }
        var expanded by remember { mutableStateOf(false) }

        TextField(
            value = ingredientName,
            onValueChange = {
                ingredientName = it
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Row{
            TextField(
                value = amount,
                onValueChange = {
                    amount = it
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                TextField(
                    value = selectedUnit.toString(),
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    val units = Unit.values()
                    units.forEach {unit ->
                        DropdownMenuItem(
                            text = { Text(unit.toString()) },
                            onClick = {
                                selectedUnit = unit
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
        Button (
            onClick = {
                val ingredient = Ingredient(name = ingredientName, amount = amount.toDouble(), unit = selectedUnit)
                ingredients.add(ingredient)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add")
        }
    }
}