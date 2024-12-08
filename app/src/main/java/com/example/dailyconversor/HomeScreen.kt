package com.example.dailyconversor

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dailyconversor.databinding.ActivityHomeScreenBinding

class HomeScreen : AppCompatActivity() {

    private lateinit var binding: ActivityHomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnIniciar = binding.btnIniciar

        btnIniciar.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}