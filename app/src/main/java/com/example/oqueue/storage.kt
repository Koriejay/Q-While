package com.example.oqueue

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class storage : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var clearButton: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)

        clearButton = findViewById(R.id.clear_button)
        backButton = findViewById(R.id.back_arrow)

        backButton.setOnClickListener {
            finish()
        }
        clearButton.setOnClickListener {
            // show progress bar
            // set the textviews to show 0s
            // hide progress bar
            Toast.makeText(applicationContext, "App data and cache cleared", Toast.LENGTH_LONG).show()
        }
    }
}
