package com.arjun.bluffer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjun.bluffer.network.MemeImage
import com.arjun.bluffer.network.MemeImageApi
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {

    private val _memeImage = MutableLiveData<MemeImage>()
    val memeImage: LiveData<MemeImage> = _memeImage

    init {
        getNewImage()
    }

    private fun getNewImage() {
        viewModelScope.launch {
            _memeImage.value = MemeImageApi.retrofitService.getRandomPhoto()
        }
    }

    private var _playerOne = MutableLiveData("Player One")
    val playerOne: LiveData<String> = _playerOne

    private var _playerTwo = MutableLiveData("Player Two")
    val playerTwo: LiveData<String> = _playerTwo

    fun playersName(playerOneName: String, playerTwoName: String) {
        _playerOne.value = playerOneName
        _playerTwo.value = playerTwoName
    }

}