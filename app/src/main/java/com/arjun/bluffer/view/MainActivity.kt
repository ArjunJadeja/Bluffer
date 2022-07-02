package com.arjun.bluffer.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arjun.bluffer.R
import com.arjun.bluffer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Bluffer)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}