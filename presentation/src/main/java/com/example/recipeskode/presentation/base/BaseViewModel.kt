package com.example.recipeskode.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel(){
    protected var _internetError= SingleLiveEvent<Boolean>()
    var internetError: LiveData<Boolean> = _internetError

    protected var _apiError= SingleLiveEvent<Boolean>()
    var apiError: LiveData<Boolean> = _apiError

    protected var _noError = SingleLiveEvent<Boolean>()
    var noError: LiveData<Boolean> = _noError

    protected var isError = false
}