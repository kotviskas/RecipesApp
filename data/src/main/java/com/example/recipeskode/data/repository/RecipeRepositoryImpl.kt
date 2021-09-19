package com.example.recipeskode.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.example.recipeskode.data.network.RepoApi
import com.example.recipeskode.domain.base.Result
import com.example.recipeskode.domain.entity.Recipe
import com.example.recipeskode.domain.repository.RecipeRepository


class RecipeRepositoryImpl(private val repoApi: RepoApi) : RecipeRepository {

    override suspend fun getRecipeList(): Result<ArrayList<Recipe>> {
        val recipes: Result<ArrayList<Recipe>> = try {
            val newRecipes = repoApi.getRecipesList()
            Result.Success(newRecipes.recipes)
        } catch (e: Exception) {
            Log.d("Error while get recipes", e.message.toString())
            Result.Error("Api problem")
        }
        return recipes
    }

    override fun hasInternetConnection(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }


}