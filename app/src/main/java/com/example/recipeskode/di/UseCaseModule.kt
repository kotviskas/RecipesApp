package com.example.recipeskode.di

import com.example.recipeskode.data.repository.RecipeRepositoryImpl
import com.example.recipeskode.domain.repository.RecipeRepository
import com.example.recipeskode.domain.usecase.CheckInternetConnectionUseCase
import com.example.recipeskode.domain.usecase.GetRecipeListUseCaseSuspend
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetRecipeListUseCaseSuspend(get())}
    factory { CheckInternetConnectionUseCase(get())}

}