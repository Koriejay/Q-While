package com.example.oqueue

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class backup : AppCompatActivity() {
    private lateinit var backButton: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backup)

        backButton = findViewById(R.id.back_arrow)
        backButton.setOnClickListener {
            finish()
        }
    }
}
