package com.example.mealplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mealplanner.ui.theme.MealPlannerTheme
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.File

class MainActivity : ComponentActivity() {

    private val recipesFileName = "recipes"
    private val chosenRecipesFileName = "chosenRecipes"

    private var recipes = ArrayList<Recipe>()
    private var chosenRecipes = ArrayList<Recipe>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recipes = readList(recipesFileName, filesDir)
        chosenRecipes = readList(chosenRecipesFileName, filesDir)

        setContent{
            MealPlannerTheme {
                Navigation(recipes = recipes, chosenRecipes = chosenRecipes)
            }
        }
    }

    override fun onStop() {
        super.onStop()

        writeList(recipesFileName, filesDir, recipes)
        writeList(chosenRecipesFileName, filesDir, chosenRecipes)
    }
}

fun readList(address: String, filesDir: File): ArrayList<Recipe> {
    val file = File(filesDir.absolutePath, address)

    if(!file.exists()) {
        return ArrayList<Recipe>()
    }

    val jsonStr = file.readText()

    val gson = GsonBuilder().create()
    return gson.fromJson<ArrayList<Recipe>>(jsonStr, object : TypeToken<ArrayList<Recipe>>(){}.type)
}

fun writeList(address: String, filesDir: File, list: ArrayList<Recipe>) {
    val gson = Gson()
    val json = gson.toJson(list, object : TypeToken<ArrayList<Recipe>>(){}.type)

    val file = File(filesDir.absolutePath, address)
    file.writeText(json.toString())
}
