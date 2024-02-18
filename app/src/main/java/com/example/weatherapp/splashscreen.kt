package com.example.weatherapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.databinding.ActivitySplashscreenBinding

class splashscreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashscreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.startbtn.setOnClickListener {
            val intent = Intent(this, MainScreen::class.java)
            startActivity(intent)

        }


    }


}