package com.example.recipeskode.domain.usecase

import com.example.recipeskode.domain.base.Result
import com.example.recipeskode.domain.base.SuspendUseCase
import com.example.recipeskode.domain.entity.RecipeDetails
import com.example.recipeskode.domain.repository.RecipeRepository

class GetRecipeInfoUseCaseSuspend(private val recipeRepository: RecipeRepository) :
    SuspendUseCase<Result<RecipeDetails>, GetRecipeInfoParams> {
    override suspend fun invoke(params: GetRecipeInfoParams): Result<RecipeDetails> {
        return recipeRepository.getRecipeInfo(params.uuid)
    }
}

class GetRecipeInfoParams(val uuid: String)