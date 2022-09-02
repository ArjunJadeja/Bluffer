package com.arjun.bluffer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjun.bluffer.network.Image
import com.arjun.bluffer.network.ImageRepository
import kotlinx.coroutines.launch
import okio.IOException

class SharedViewModel : ViewModel() {

    //    Network Status
    private val _isNetworkConnected = MutableLiveData<Boolean>()
    val isNetworkConnected: LiveData<Boolean> = _isNetworkConnected

    fun getNetwork(isNetworkConnected: Boolean) {
        _isNetworkConnected.value = isNetworkConnected
    }

    //    Image Data
    private val _image = MutableLiveData<Image>()
    val image: LiveData<Image> = _image

    private val _imageStatus = MutableLiveData<Boolean>()
    val imageStatus: LiveData<Boolean> = _imageStatus

    private val repository: ImageRepository = ImageRepository()

    fun getNewImage() {
        if (isNetworkConnected.value == true) {
            viewModelScope.launch {
                try {
                    _image.value = repository.getRandomImage()
                    if (image.value?.nsfw == true) {
                        getNewImage()
                    }
                } catch (e: IOException) {
                    _imageStatus.value = false
                }
            }
        }
    }

    //    Player Names
    private val _playerOne = MutableLiveData("")
    val playerOne: LiveData<String> = _playerOne

    private val _playerTwo = MutableLiveData("")
    val playerTwo: LiveData<String> = _playerTwo

    fun playersName(playerOneName: String, playerTwoName: String) {
        _playerOne.value = playerOneName
        _playerTwo.value = playerTwoName
    }

    //    Player Roles
    private val _explainer = MutableLiveData("")
    val explainer: LiveData<String> = _explainer

    private val _guesser = MutableLiveData("")
    val guesser: LiveData<String> = _guesser

    fun playersRole(playerOneName: String, playerTwoName: String) {
        val playerList = listOf(playerOneName, playerTwoName)
        _explainer.value = selectedPlayer(playerList)
        _guesser.value =
            if (playerList.first() == explainer.value) {
                playerList.last()
            } else {
                playerList.first()
            }
    }

    private fun selectedPlayer(playerList: List<String>): String {
        return playerList.random()
    }

    //    Sound Setting
    private val _soundOn = MutableLiveData(true)
    val soundOn: LiveData<Boolean> = _soundOn

    fun soundSetting(soundOn: Boolean) {
        _soundOn.value = soundOn
    }

}