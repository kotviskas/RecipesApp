package com.example.recipeskode.di

import com.example.recipeskode.R
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    factory {
        (androidContext().resources.getString(R.string.app_name))
    }
}