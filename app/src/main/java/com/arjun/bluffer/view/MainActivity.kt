package com.arjun.bluffer.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.arjun.bluffer.R
import com.arjun.bluffer.databinding.ActivityMainBinding
import com.arjun.bluffer.utils.NetworkConnected
import com.arjun.bluffer.viewmodel.SharedViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Bluffer)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  Clearing Cache from previous games
        this.cacheDir.deleteRecursively()

        //  Sending Network Status to SharedViewModel
        val networkConnected = NetworkConnected(this)
        networkConnected.observe(this) {
            sharedViewModel.getNetwork(it)
        }

    }

}