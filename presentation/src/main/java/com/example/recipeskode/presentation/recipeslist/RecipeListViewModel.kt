package com.example.recipeskode.presentation.recipeslist

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeskode.domain.base.SortOptions
import com.example.recipeskode.domain.entity.Recipe
import com.example.recipeskode.domain.usecase.GetRecipeListParams
import com.example.recipeskode.domain.usecase.GetRecipeListUseCaseSuspend
import com.example.recipeskode.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

class RecipeListViewModel(
    private val getRecipeListUseCase: GetRecipeListUseCaseSuspend,
    private val application: Application
) : BaseViewModel() {

    private var _recipes: MutableLiveData<ArrayList<Recipe>> =
        MutableLiveData()
    val recipes: LiveData<ArrayList<Recipe>> = _recipes

    private var sortOption = SortOptions.BY_NAME
    private var _searchWord: MutableLiveData<String> = MutableLiveData()
    val searchWord = _searchWord
    private var recipesList: ArrayList<Recipe> = ArrayList()

    init {
        getRecipeList()
    }

    fun setOptionByName() {
        sortOption = SortOptions.BY_NAME
    }

    fun setOptionByDate() {
        sortOption = SortOptions.BY_DATE
    }

    fun getRecipeList() {
        viewModelScope.launch {
            getRecipeListSafeCall()
            sortRecipe()
            _searchWord.value?.let { searchRecipe(it) }
        }
    }

    fun sortRecipe() {
        if (sortOption == SortOptions.BY_NAME) {
            _recipes.value?.sortBy { it.name }
        } else {
            _recipes.value?.sortBy { it.lastUpdated }
        }
    }

    fun searchAndSortRecipesList(text: String) {
        if (text != "") {
            _searchWord.value = text
        }
        searchRecipe(text)
        sortRecipe()
    }


    private fun searchRecipe(text: String) {
        _recipes.value = recipesList.filter {
            it.name.contains(text, true)
                    || it.description?.contains(text, true) ?: false
                    || it.instructions?.contains(text, true) ?: false
        } as ArrayList<Recipe>

    }

    private suspend fun getRecipeListSafeCall() {
        if (hasInternetConnection(application)) {
            val result = getRecipeListUseCase.invoke(GetRecipeListParams())
            result
                .onFailure {
                    _apiError.call()
                    isError = true
                }
                .onSuccess {
                    recipesList = result.getOrDefault(ArrayList())
                    _recipes.value = recipesList
                    if (isError) {
                        _noError.call()
                        isError = false
                    }
                }
        } else {
            _internetError.call()
            isError = true
        }
    }
}