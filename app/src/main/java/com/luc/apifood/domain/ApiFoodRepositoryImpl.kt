package com.luc.apifood.domain

import com.luc.apifood.core.Resource
import com.luc.apifood.data.RetrofitDataSource
import com.luc.apifood.data.model.RecipeInformation
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ActivityRetainedScoped
class ApiFoodRepositoryImpl @Inject constructor(private val dataSource: RetrofitDataSource) :
    ApiFoodRepository {

    override suspend fun getRecipesByType(type: String) = dataSource.getRecipesByType(type)
    override suspend fun getRecipeInformation(recipeId: Int) = dataSource.getRecipeInformation(recipeId)
}