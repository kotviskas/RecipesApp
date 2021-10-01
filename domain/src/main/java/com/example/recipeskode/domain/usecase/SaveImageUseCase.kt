package com.example.recipeskode.domain.usecase

import android.content.Context
import android.graphics.Bitmap
import com.example.recipeskode.domain.base.BaseUseCase
import com.example.recipeskode.domain.repository.RecipeRepository

class SaveImageUseCase(private val recipeRepository: RecipeRepository):BaseUseCase<Boolean,SaveImageParams > {
    override fun invoke(params: SaveImageParams): Boolean {
        return recipeRepository.saveImage(params.bitmap,params.context)
    }
}

class SaveImageParams(val bitmap: Bitmap, val context: Context)