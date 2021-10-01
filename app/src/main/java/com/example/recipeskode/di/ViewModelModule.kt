package com.example.recipeskode.di

import com.example.recipeskode.presentation.photo.RecipePhotoViewModel
import com.example.recipeskode.presentation.recipeinfo.RecipeInfoViewModel
import com.example.recipeskode.presentation.recipeslist.RecipeListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        RecipeListViewModel(get(), get(), get())
    }
    viewModel {
        RecipeInfoViewModel(get(), get(), get())
    }
    viewModel {
        RecipePhotoViewModel(get(), get())
    }
}







