package com.example.recipeskode.domain.usecase

import android.content.Context
import com.example.recipeskode.domain.base.BaseUseCase

import com.example.recipeskode.domain.repository.RecipeRepository

class CheckInternetConnectionUseCase(private val recipeRepository: RecipeRepository) :
    BaseUseCase<Boolean, CheckInternetConnectionParams> {

    override fun invoke(params: CheckInternetConnectionParams): Boolean {
        return recipeRepository.hasInternetConnection(params.context)
    }
}

class CheckInternetConnectionParams(val context: Context)