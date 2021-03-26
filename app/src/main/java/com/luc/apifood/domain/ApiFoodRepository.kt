package com.luc.apifood.domain

import com.luc.apifood.core.Resource
import com.luc.apifood.data.model.Recipe
import com.luc.apifood.data.model.RecipeInformation

interface ApiFoodRepository {
    suspend fun getRecipesByType(type: String): Resource<List<Recipe>>
    suspend fun getRecipeInformation(recipeId: Int): Resource<RecipeInformation>
}