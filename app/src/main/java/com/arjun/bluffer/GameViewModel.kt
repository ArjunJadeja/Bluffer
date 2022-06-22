package com.arjun.bluffer

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjun.bluffer.network.MemeImage
import com.arjun.bluffer.network.MemeImageApi
import kotlinx.coroutines.launch

class GameViewModel: ViewModel() {

    private lateinit var timer: CountDownTimer

    private val _seconds = MutableLiveData<Int>()
    val seconds: LiveData<Int> = _seconds

    private var _finished = MutableLiveData<Boolean>()
    val finished: LiveData<Boolean> = _finished

    fun startTimer(){
        timer = object : CountDownTimer(45000,1000) {

            override fun onTick(p0: Long) {
                val timeLeft = p0/1000
                _seconds.value = timeLeft.toInt()
            }
            override fun onFinish() {
                _finished.value = true
            }

        }.start()
    }
    fun stopTimer(){
        timer.cancel()
    }

}