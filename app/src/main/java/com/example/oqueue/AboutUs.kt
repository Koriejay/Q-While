package com.example.oqueue

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class AboutUs : AppCompatActivity() {
    private lateinit var webV: WebView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        webV = findViewById(R.id.website)
        webV.loadUrl("file:///android_asset/home.html")
    }
}