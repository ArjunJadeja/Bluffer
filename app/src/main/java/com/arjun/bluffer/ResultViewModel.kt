package com.arjun.bluffer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel : ViewModel() {

    private val _explained = MutableLiveData<Boolean>()
    val explained: LiveData<Boolean> = _explained

    private val _guessed = MutableLiveData<Boolean>()
    val guessed: LiveData<Boolean> = _guessed

    fun checkResult (playerOneName: Boolean, playerTwoName: Boolean) {
        _explained.value = explained.value
        _guessed.value = guessed.value
    }

    val result =
        if (explained == guessed) "Lost"
        else "Won"

}