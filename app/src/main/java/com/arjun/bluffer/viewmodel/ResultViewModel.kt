package com.arjun.bluffer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel : ViewModel() {

    private val explained = MutableLiveData<Boolean>()

    private val guessed = MutableLiveData<Boolean>()

    fun checkResult (playerOneName: Boolean, playerTwoName: Boolean) {
        explained.value = playerOneName
        guessed.value = playerTwoName
    }

    val result =
        if (explained == guessed) "Lost"
        else "Lost"

}