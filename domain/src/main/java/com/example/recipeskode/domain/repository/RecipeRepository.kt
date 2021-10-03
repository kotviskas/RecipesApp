package com.example.recipeskode.domain.repository

import com.example.recipeskode.domain.base.Result
import com.example.recipeskode.domain.entity.Recipe
import com.example.recipeskode.domain.entity.RecipeDetails

interface RecipeRepository {
    suspend fun getRecipeList(): Result<List<Recipe>>
    suspend fun getRecipeInfo(uuid: String): Result<RecipeDetails>
    fun saveImage(byteArray: ByteArray): Boolean
}