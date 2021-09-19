package com.example.recipeskode.di

import com.example.recipeskode.data.repository.RecipeRepositoryImpl
import com.example.recipeskode.domain.repository.RecipeRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule = module {
    single<RecipeRepository> { RecipeRepositoryImpl(get()) }
   // factory { RecipeRepositoryImpl(get()) }
}