package com.example.recipeskode.domain.usecase

import com.example.recipeskode.domain.base.Result
import com.example.recipeskode.domain.base.SuspendUseCase
import com.example.recipeskode.domain.entity.Recipe
import com.example.recipeskode.domain.repository.RecipeRepository

class GetRecipeListUseCaseSuspend(private val recipeRepository: RecipeRepository) :
    SuspendUseCase<Result<List<Recipe>>, GetRecipeListParams> {
    override suspend fun invoke(params: GetRecipeListParams): Result<List<Recipe>> {
        return recipeRepository.getRecipeList()
    }
}

class GetRecipeListParams