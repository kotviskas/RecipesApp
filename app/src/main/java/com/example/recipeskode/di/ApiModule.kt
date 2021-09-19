package com.example.recipeskode.di

import com.example.recipeskode.data.network.RepoApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    fun provideUseApi(retrofit: Retrofit): RepoApi {
        return retrofit.create(RepoApi::class.java)
    }
    single { provideUseApi(get()) }
}