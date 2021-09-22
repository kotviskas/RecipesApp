package com.example.recipeskode.di

import com.example.recipeskode.data.repository.RecipeRepositoryImpl
import com.example.recipeskode.domain.repository.RecipeRepository
import com.example.recipeskode.domain.usecase.*
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetRecipeListUseCaseSuspend(get())}
    factory { CheckInternetConnectionUseCase(get())}
    factory { GetRecipeInfoUseCaseSuspend(get()) }
    factory { SaveImageUseCase(get()) }
}