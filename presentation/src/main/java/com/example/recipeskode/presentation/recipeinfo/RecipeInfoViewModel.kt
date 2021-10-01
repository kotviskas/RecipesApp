package com.example.recipeskode.presentation.recipeinfo

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeskode.domain.base.Result
import com.example.recipeskode.domain.entity.RecipeDetails
import com.example.recipeskode.domain.usecase.CheckInternetConnectionParams
import com.example.recipeskode.domain.usecase.CheckInternetConnectionUseCase
import com.example.recipeskode.domain.usecase.GetRecipeInfoParams
import com.example.recipeskode.domain.usecase.GetRecipeInfoUseCaseSuspend
import com.example.recipeskode.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class RecipeInfoViewModel(
    private val getRecipeInfoUseCaseSuspend: GetRecipeInfoUseCaseSuspend,
    private val checkInternetConnectionUseCase: CheckInternetConnectionUseCase,
    private val application: Application
) : BaseViewModel() {

    private var _recipe: MutableLiveData<RecipeDetails> = MutableLiveData()
    var recipe: LiveData<RecipeDetails> = _recipe


    fun getRecipe(uuid: String) {
        viewModelScope.launch {
            if (getRecipeSafeCall(uuid) != null) {
                _recipe.value = getRecipeSafeCall(uuid)
                getSimilarRecipesPhotos()
            }
        }
    }

    private fun getSimilarRecipesPhotos() {
        val newRecipe = _recipe.value
        viewModelScope.launch {
            newRecipe?.similar?.forEach {
                val newSimilarRecipe = getRecipeSafeCall(it.uuid)
                if (newSimilarRecipe != null) {
                    it.images = newSimilarRecipe.images
                }
            }
            if (newRecipe != null) {
                _recipe.value = newRecipe!!
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        return checkInternetConnectionUseCase.invoke(CheckInternetConnectionParams(application))
    }

    private suspend fun getRecipeSafeCall(uuid: String): RecipeDetails? {
        if (hasInternetConnection()) {
            when (val result = getRecipeInfoUseCaseSuspend.invoke(GetRecipeInfoParams(uuid))) {
                is Result.Error -> {
                    _apiError.call()
                    isError = true
                }
                else -> {
                    if (result.data != null) {
                        if (isError) {
                            _noError.call()
                            isError = false
                        }
                        return result.data
                    }
                }
            }
        } else {
            _internetError.call()
            isError = true
        }
        return null
    }
}