package com.example.recipeskode.domain.usecase

import com.example.recipeskode.domain.base.UseCase
import com.example.recipeskode.domain.repository.RecipeRepository

class SaveImageUseCase(private val recipeRepository: RecipeRepository) :
    UseCase<Boolean, SaveImageParams> {
    override fun invoke(params: SaveImageParams): Boolean {
        return recipeRepository.saveImage(params.byteArray)
    }
}

class SaveImageParams(val byteArray: ByteArray)