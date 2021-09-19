package com.example.recipeskode.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recipeskode.presentation.recipeslist.RecipeListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModel<RecipeListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}