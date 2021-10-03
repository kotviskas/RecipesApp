package com.example.recipeskode.data.repository

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.example.recipeskode.data.network.RecipeRepoApi
import com.example.recipeskode.domain.base.Result
import com.example.recipeskode.domain.entity.Recipe
import com.example.recipeskode.domain.entity.RecipeDetails
import com.example.recipeskode.domain.repository.RecipeRepository
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


class RecipeRepositoryImpl(private val repoApi: RecipeRepoApi, private val context: Context) :
    RecipeRepository {

    override suspend fun getRecipeList(): Result<List<Recipe>> {
        val recipes: Result<List<Recipe>> = try {
            val newRecipes = repoApi.getRecipesList()
            newRecipes.recipes.forEach {
                it.lastUpdated = convertUnixToTime(it.lastUpdated.toLong())
            }
            Result.Success(newRecipes.recipes)
        } catch (e: Exception) {
            Log.d("Error while get recipes", e.message.toString())
            Result.Error("Api problem")
        }
        return recipes
    }

    override suspend fun getRecipeInfo(uuid: String): Result<RecipeDetails> {
        val recipe = try {
            val newRecipe = repoApi.getRecipe(uuid)
            newRecipe.recipe.instructions = newRecipe.recipe.instructions.replace("<br>", "\n")
            newRecipe.recipe.lastUpdated = convertUnixToTime(newRecipe.recipe.lastUpdated.toLong())
            Result.Success(newRecipe.recipe)
        } catch (e: java.lang.Exception) {
            Log.d("Error while get recipe", e.message.toString())
            Result.Error("Api problem")
        }
        return recipe
    }

    override fun saveImage(byteArray: ByteArray): Boolean {
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
            fOs?.write(byteArray)
        } else {
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    .toString() + File.separator,
                "$name.png"
            )
            fOs = FileOutputStream(file)
            fOs.write(byteArray)
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

    private fun convertUnixToTime(time: Long): String {
        val date = Date(time * 1000)
        val format = SimpleDateFormat("dd.MM.yyyy")
        return format.format(date)
    }

}