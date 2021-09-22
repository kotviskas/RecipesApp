package com.example.recipeskode.domain.repository

import android.content.Context
import android.graphics.Bitmap
import com.example.recipeskode.domain.base.Result
import com.example.recipeskode.domain.entity.Recipe
import com.example.recipeskode.domain.entity.RecipeDetails

interface RecipeRepository {
    suspend fun getRecipeList(): Result<ArrayList<Recipe>>
    fun hasInternetConnection(context: Context?): Boolean
    suspend fun getRecipeInfo(uuid: String) : Result<RecipeDetails>
    fun saveImage(bitmap: Bitmap, context: Context): Boolean
}