package com.example.oqueue

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class Theme : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var lightModeRadioButton: RadioButton
    private lateinit var darkModeRadioButton: RadioButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme)

        lightModeRadioButton = findViewById(R.id.btn_light)
        darkModeRadioButton = findViewById(R.id.btn_dark)
        backButton = findViewById(R.id.back_arrow)
        backButton.setOnClickListener {
            finish()
        }

        lightModeRadioButton.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            recreate()
        }

        darkModeRadioButton.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            recreate()
        }
    }
}