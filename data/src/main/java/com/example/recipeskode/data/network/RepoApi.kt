package com.example.recipeskode.data.network

import com.example.recipeskode.data.network.dto.RecipeDetailsDTO
import com.example.recipeskode.data.network.dto.RecipeDTO
import retrofit2.http.*

interface RepoApi {

    @GET("/recipes")
    suspend fun getRecipesList(): RecipeDTO

    @GET("/recipes/{id}")
    suspend fun getRecipe(@Path("id") id: String): RecipeDetailsDTO

}