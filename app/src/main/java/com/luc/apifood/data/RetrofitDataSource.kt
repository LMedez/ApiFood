package com.luc.apifood.data

import com.luc.apifood.core.Resource
import com.luc.apifood.data.model.Recipe
import com.luc.apifood.data.model.RecipeInformation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class RetrofitDataSource @Inject constructor(
    private val webServiceRecipeInformation: RecipeInformationWebService,
) {

    private val lastsRecipesByType = mutableMapOf<String, List<Recipe>>()
    private var lastRecipeInformation = mutableMapOf<Int, RecipeInformation>()

    suspend fun getRecipesByType(typeName: String): Resource<List<Recipe>> {
        return if (lastsRecipesByType[typeName].isNullOrEmpty()) {
            lastsRecipesByType[typeName] =
                webServiceRecipeInformation.getRecipesByType(typeName).recipeList
            Resource.Success(lastsRecipesByType[typeName]!!)
        } else Resource.Success(lastsRecipesByType[typeName]!!)
    }

    suspend fun getRecipeInformation(recipeId: Int): Resource<RecipeInformation> {
        return if (lastRecipeInformation[recipeId]?.diets.isNullOrEmpty()) {
            lastRecipeInformation[recipeId] = webServiceRecipeInformation.getRecipeInformation(recipeId)
            val nutritionFiltered =
                lastRecipeInformation[recipeId]!!.nutrition.nutrients.filter { it.name == "Protein" || it.name == "Calories" || it.name == "Fat" }
            lastRecipeInformation[recipeId]!!.nutrition.nutrients = nutritionFiltered
            Resource.Success(lastRecipeInformation[recipeId]!!)
        } else {
            Resource.Success(lastRecipeInformation[recipeId]!!)
        }
    }

}