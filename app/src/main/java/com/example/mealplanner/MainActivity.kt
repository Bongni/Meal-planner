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
    private val ingredientsFileName = "ingredients"

    private var recipes = ArrayList<Recipe>()
    private var ingredients = ArrayList<Ingredient>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recipes = readListRecipe(recipesFileName, filesDir)
        ingredients = readListIngredient(ingredientsFileName, filesDir)

        setContent{
            MealPlannerTheme {
                Navigation(recipes = recipes, ingredients = ingredients)
            }
        }
    }

    override fun onStop() {
        super.onStop()

        writeListRecipe(recipesFileName, filesDir, recipes)
        writeListIngredient(ingredientsFileName, filesDir, ingredients)
    }
}

fun readListRecipe(address: String, filesDir: File): ArrayList<Recipe> {
    val file = File(filesDir.absolutePath, address)

    if(!file.exists()) {
        return ArrayList<Recipe>()
    }

    val jsonStr = file.readText()

    val gson = GsonBuilder().create()
    return gson.fromJson<ArrayList<Recipe>>(jsonStr, object : TypeToken<ArrayList<Recipe>>(){}.type)
}

fun readListIngredient(address: String, filesDir: File): ArrayList<Ingredient> {
    val file = File(filesDir.absolutePath, address)

    if(!file.exists()) {
        return ArrayList<Ingredient>()
    }

    val jsonStr = file.readText()

    val gson = GsonBuilder().create()
    return gson.fromJson<ArrayList<Ingredient>>(jsonStr, object : TypeToken<ArrayList<Ingredient>>(){}.type)
}

fun writeListRecipe(address: String, filesDir: File, list: ArrayList<Recipe>) {
    val gson = Gson()
    val json = gson.toJson(list, object : TypeToken<ArrayList<Recipe>>(){}.type)

    val file = File(filesDir.absolutePath, address)
    file.writeText(json.toString())
}

fun writeListIngredient(address: String, filesDir: File, list: ArrayList<Ingredient>) {
    val gson = Gson()
    val json = gson.toJson(list, object : TypeToken<ArrayList<Ingredient>>(){}.type)

    val file = File(filesDir.absolutePath, address)
    file.writeText(json.toString())
}
