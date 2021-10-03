package com.example.recipeskode.di

import com.example.recipeskode.data.network.RecipeRepoApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun provideUseApi(retrofit: Retrofit): RecipeRepoApi {
        return retrofit.create(RecipeRepoApi::class.java)
    }
    single { provideUseApi(get()) }
}