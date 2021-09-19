package com.example.recipeskode.domain.entity


import com.google.gson.annotations.SerializedName

data class RecipeDetails(
    @SerializedName("description")
    val description: String,
    @SerializedName("difficulty")
    val difficulty: String,
    @SerializedName("images")
    val images: ArrayList<String>,
    @SerializedName("instructions")
    val instructions: String,
    @SerializedName("lastUpdated")
    val lastUpdated: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("similar")
    val similar: ArrayList<RecipeBrief>,
    @SerializedName("uuid")
    val uuid: String
)