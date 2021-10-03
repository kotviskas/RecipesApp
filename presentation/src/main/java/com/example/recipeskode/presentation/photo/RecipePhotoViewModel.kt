package com.example.recipeskode.presentation.photo

import android.graphics.Bitmap
import com.example.recipeskode.domain.usecase.SaveImageParams
import com.example.recipeskode.domain.usecase.SaveImageUseCase
import com.example.recipeskode.presentation.base.BaseViewModel
import java.io.ByteArrayOutputStream

class RecipePhotoViewModel(private val saveImageUseCase: SaveImageUseCase) : BaseViewModel() {

    fun saveImage(bitmap: Bitmap) {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        val image = stream.toByteArray()
        saveImageUseCase.invoke(SaveImageParams(image))
    }
}