package com.luc.apifood.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.luc.apifood.core.Resource
import com.luc.apifood.data.model.RecipeInformation
import com.luc.apifood.domain.ApiFoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val foodRepository: ApiFoodRepository
) : ViewModel() {

    fun getRecipeInformation(recipeId: Int) = liveData<Resource<RecipeInformation>>(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(foodRepository.getRecipeInformation(recipeId))
        } catch (e: Exception) {
            emit(Resource.Error(e, "ERROR"))
        }
    }
}