package com.example.recipeskode.data.network

import com.example.recipeskode.data.network.dto.RecipeDTO
import com.example.recipeskode.data.network.dto.RecipeDetailsDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeRepoApi {

    @GET("/recipes")
    suspend fun getRecipesList(): RecipeDTO

    @GET("/recipes/{id}")
    suspend fun getRecipe(@Path("id") id: String): RecipeDetailsDTO

}