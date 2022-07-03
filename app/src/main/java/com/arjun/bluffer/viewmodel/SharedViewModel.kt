package com.arjun.bluffer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjun.bluffer.network.MemeImage
import com.arjun.bluffer.network.MemeImageApi
import kotlinx.coroutines.launch
import okio.IOException

class SharedViewModel : ViewModel() {

    private val _memeImage = MutableLiveData<MemeImage>()
    val memeImage: LiveData<MemeImage> = _memeImage

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    fun getNewImage() {
        viewModelScope.launch {
            try {
                _memeImage.value = MemeImageApi.retrofitService.getRandomPhoto()
            } catch (e: IOException) {
                _status.value = e.toString()
            }
        }
    }

    private var _playerOne = MutableLiveData("")
    val playerOne: LiveData<String> = _playerOne

    private var _playerTwo = MutableLiveData("")
    val playerTwo: LiveData<String> = _playerTwo

    fun playersName(playerOneName: String, playerTwoName: String) {
        _playerOne.value = playerOneName
        _playerTwo.value = playerTwoName
    }

}