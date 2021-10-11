package com.example.recipeskode.domain.usecase

import com.example.recipeskode.domain.base.SuspendUseCase
import com.example.recipeskode.domain.entity.Recipe
import com.example.recipeskode.domain.repository.RecipeRepository

class GetRecipeListUseCaseSuspend(private val recipeRepository: RecipeRepository) :
    SuspendUseCase<Result<ArrayList<Recipe>>, GetRecipeListParams> {
    override suspend fun invoke(params: GetRecipeListParams): Result<ArrayList<Recipe>> {
        return recipeRepository.getRecipeList()
    }
}

class GetRecipeListParams