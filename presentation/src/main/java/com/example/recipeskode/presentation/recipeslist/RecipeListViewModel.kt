package com.example.recipeskode.presentation.recipeslist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeskode.domain.base.Result
import com.example.recipeskode.domain.base.SortOptions
import com.example.recipeskode.domain.entity.Recipe
import com.example.recipeskode.domain.usecase.CheckInternetConnectionParams
import com.example.recipeskode.domain.usecase.CheckInternetConnectionUseCase
import com.example.recipeskode.domain.usecase.GetRecipeListParams
import com.example.recipeskode.domain.usecase.GetRecipeListUseCaseSuspend
import com.example.recipeskode.presentation.base.BaseViewModel
import com.example.recipeskode.presentation.base.SingleLiveEvent
import kotlinx.coroutines.launch

class RecipeListViewModel(
    private val getRecipeListUseCase: GetRecipeListUseCaseSuspend,
    private val checkInternetConnectionUseCase: CheckInternetConnectionUseCase,
    private val application: Application
) : BaseViewModel() {

    private var _recipes: MutableLiveData<ArrayList<Recipe>> =
        MutableLiveData()
    var recipes: LiveData<ArrayList<Recipe>> = _recipes

    private var _internetError= SingleLiveEvent<Boolean>()
    var internetError: LiveData<Boolean> = _internetError

    private var _apiError= SingleLiveEvent<Boolean>()
    var apiError: LiveData<Boolean> = _apiError

    private var _noError = SingleLiveEvent<Boolean>()
    var noError: LiveData<Boolean> = _noError

    private var sortOption = SortOptions.BY_NAME
    private var searchWord = ""
    private var isError = false
    private lateinit var recipesList : ArrayList<Recipe>

    init {
        getRecipeList()
    }

    fun setOptionByName() {
        sortOption = SortOptions.BY_NAME
    }

    fun setOptionByDate() {
        sortOption = SortOptions.BY_DATE
    }

    private fun hasInternetConnection(): Boolean {
        return checkInternetConnectionUseCase.invoke(CheckInternetConnectionParams(application))
    }

    fun getRecipeList() {
        viewModelScope.launch {
            getRecipeListSafeCall()
            sortRecipe()
            searchRecipe(searchWord)
        }
    }

    fun sortRecipe() {
        if (sortOption == SortOptions.BY_NAME) {
            _recipes.value?.sortBy { it.name }
        } else {
            _recipes.value?.sortBy { it.lastUpdated }
        }
    }

    fun searchAndSortRecipesList(text: String){
        searchWord = text
        searchRecipe(searchWord)
        sortRecipe()
    }

    private fun searchRecipe(text: String) {
        _recipes.value = recipesList.filter {
            it.name.contains(searchWord, true)
                    || it.description?.contains(searchWord, true) ?: false
                    || it.instructions?.contains(searchWord, true) ?: false
        } as ArrayList<Recipe>
    }

    private suspend fun getRecipeListSafeCall() {
        if (hasInternetConnection()) {
            when (val result = getRecipeListUseCase.invoke(GetRecipeListParams())) {
                is Result.Error -> {
                    _apiError.call()
                    isError = true
                }
                else -> {
                    if (result.data != null) {
                        recipesList = result.data!!
                        _recipes.value = recipesList
                        if (isError) {
                            _noError.call()
                            isError = false
                        }

                    }
                }
            }

        } else {
            _internetError.call()
            isError = true
        }
    }
}