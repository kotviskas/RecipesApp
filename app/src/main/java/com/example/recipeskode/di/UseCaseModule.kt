package com.example.recipeskode.di

import com.example.recipeskode.domain.usecase.GetRecipeInfoUseCaseSuspend
import com.example.recipeskode.domain.usecase.GetRecipeListUseCaseSuspend
import com.example.recipeskode.domain.usecase.SaveImageUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetRecipeListUseCaseSuspend(get()) }
    factory { GetRecipeInfoUseCaseSuspend(get()) }
    factory { SaveImageUseCase(get()) }
}