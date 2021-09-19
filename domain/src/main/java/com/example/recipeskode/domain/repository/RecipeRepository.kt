package com.example.recipeskode.domain.repository

import android.content.Context
import com.example.recipeskode.domain.base.Result
import com.example.recipeskode.domain.entity.Recipe

interface RecipeRepository {
    suspend fun getRecipeList(): Result<ArrayList<Recipe>>
    fun hasInternetConnection(context: Context?): Boolean
}