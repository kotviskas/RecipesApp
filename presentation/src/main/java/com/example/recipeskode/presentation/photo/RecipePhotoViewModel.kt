package com.example.recipeskode.presentation.photo

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.core.app.ActivityCompat
import com.example.recipeskode.domain.usecase.SaveImageParams
import com.example.recipeskode.domain.usecase.SaveImageUseCase
import com.example.recipeskode.presentation.base.BaseViewModel

class RecipePhotoViewModel(private val saveImageUseCase: SaveImageUseCase, private val application: Application): BaseViewModel() {

    private fun saveImage(bitmap: Bitmap){
        saveImageUseCase.invoke(SaveImageParams(bitmap,application))
    }

    fun saveImage(bitmap: Bitmap, activity: Activity){
        try {
            if (ActivityCompat.checkSelfPermission(
                    application,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ), 2
                )
            } else {
                saveImage(bitmap)
            }
        } catch (e: Exception) {
            //ошибка
        }
    }
}