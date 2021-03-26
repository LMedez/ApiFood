package com.luc.apifood.data

import com.luc.apifood.data.model.RecipeInformation
import com.luc.apifood.data.model.RecipeList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeInformationWebService {
    @GET("search?number=10")
    suspend fun getRecipesByType(
        @Query(value = "query") typeName: String,
    ): RecipeList

    @GET("{id}/information?includeNutrition=true")
    suspend fun getRecipeInformation(
        @Path(value = "id") id: Int,
    ): RecipeInformation
}