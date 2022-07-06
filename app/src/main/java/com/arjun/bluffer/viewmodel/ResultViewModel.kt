package com.arjun.bluffer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel : ViewModel() {

    private val _result = MutableLiveData<Boolean>()
    val result: LiveData<Boolean> = _result

    fun playerResponse(explainerResponse: Boolean, guesserResponse: Boolean) {
        _result.value = explainerResponse == guesserResponse
    }

}