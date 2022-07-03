package com.arjun.bluffer.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val MILLIS = 1000L

class GameViewModel: ViewModel() {

    private var timer: CountDownTimer? = null

    var timerValue = MutableLiveData<Long>()

    private val _seconds = MutableLiveData<Int>()
    val seconds: LiveData<Int> = _seconds

    private var _finished = MutableLiveData<Boolean>()
    val finished: LiveData<Boolean> = _finished

    fun startTimer(){
        timer = object : CountDownTimer(timerValue.value!!.toLong(), MILLIS) {

            override fun onTick(p0: Long) {
                val timeLeft = p0 / MILLIS
                _seconds.value = timeLeft.toInt()
            }
            override fun onFinish() {
                _finished.value = true
            }

        }.start()
    }
    fun stopTimer() {
        timer?.cancel()
    }

}