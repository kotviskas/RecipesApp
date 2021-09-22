package com.example.recipeskode.data.repository

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.example.recipeskode.data.network.RepoApi
import com.example.recipeskode.domain.base.Result
import com.example.recipeskode.domain.entity.Recipe
import com.example.recipeskode.domain.entity.RecipeDetails
import com.example.recipeskode.domain.repository.RecipeRepository
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


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

    override suspend fun getRecipeInfo(uuid: String): Result<RecipeDetails> {
        val recipe = try {
            val newRecipe = repoApi.getRecipe(uuid)
            Result.Success(newRecipe.recipe)
        } catch (e: java.lang.Exception) {
            Log.d("Error while get recipe", e.message.toString())
            Result.Error("Api problem")
        }
        return recipe
    }

    override fun saveImage(bitmap: Bitmap, context: Context) : Boolean{
        val fOs: OutputStream?
        val name = "${System.currentTimeMillis()}.jpg"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver: ContentResolver =
                context.contentResolver
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            fOs = resolver.openOutputStream(
                resolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
                )!!
            )
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOs)
        } else {
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    .toString() + File.separator,
                "$name.png"
            )
            fOs = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOs)
            MediaStore.Images.Media.insertImage(
                context.contentResolver,
                file.absolutePath,
                file.name,
                file.name
            )
        }
        fOs!!.flush()
        fOs.close()
        return true
    }


}