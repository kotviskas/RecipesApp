package com.example.recipeskode.domain.entity


import com.google.gson.annotations.SerializedName

data class RecipeBrief(
    @SerializedName("name")
    val name: String,
    @SerializedName("uuid")
    val uuid: String
)