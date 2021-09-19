package com.example.recipeskode.di

import com.example.recipeskode.presentation.recipeslist.RecipeListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        RecipeListViewModel(get(), get(), get())
    }
//    viewModel {
//        AboutRecipeViewModel(get())
//    }
//    viewModel {
//        RecipePhotoViewModel(get())
//    }
}







