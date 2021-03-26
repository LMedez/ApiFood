package com.luc.apifood.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.luc.apifood.core.Resource
import com.luc.apifood.data.model.Recipe
import com.luc.apifood.domain.ApiFoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val foodRepository: ApiFoodRepository
) : ViewModel() {

    fun getRecipesByType(type: String) = liveData<Resource<List<Recipe>>>(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(foodRepository.getRecipesByType(type))
        } catch (e: Exception) {
            emit(Resource.Error(e, "ERROR"))
        }
    }
}