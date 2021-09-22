package com.example.recipeskode.domain.entity


import com.google.gson.annotations.SerializedName

data class RecipeBrief(
    @SerializedName("name")
    val name: String,
    @SerializedName("images")
    var images: ArrayList<String>? = ArrayList(),
    @SerializedName("uuid")
    val uuid: String
)