package com.luc.apifood.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    val id: Int = -1,
    var title: String = "",
    val readyInMinutes: Int = 0,
    val servings: String = "",
    val image: String = "",
    val baseUrl: String = "https://spoonacular.com/recipeImages/"
) : Parcelable

data class RecipeList(
    @SerializedName("results")
    val recipeList: List<Recipe> = listOf()
)

data class Nutrients(val name: String = "", val amount: Float = 0f, val unit: String = "")

data class Nutritions(var nutrients: List<Nutrients> = listOf())

data class Ingredient(
    val name: String = "",
    val image: String = "",
    val baseUrl: String = "https://spoonacular.com/cdn/ingredients_100x100/"
)

data class RecipeInformation(
    val nutrition: Nutritions = Nutritions(),
    val diets: List<String> = listOf(),
    val summary: String = "",
    @SerializedName("extendedIngredients")
    val ingredients: List<Ingredient> = listOf()
)


