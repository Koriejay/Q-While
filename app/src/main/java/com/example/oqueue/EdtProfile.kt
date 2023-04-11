package com.example.oqueue

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EdtProfile : AppCompatActivity() {

    private lateinit var profilePicture: ImageView
    private lateinit var fullNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var telephoneEditText: EditText
    private lateinit var saveChangesButton: Button
    private lateinit var changeEmailLink: TextView
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edt_profile)

        // Find UI elements by ID
        backButton = findViewById(R.id.back_arrow)
        backButton.setOnClickListener {
            finish()
        }

        saveChangesButton = findViewById(R.id.save_changes_button)
        saveChangesButton.setOnClickListener {
            // Handle the event
        }

        profilePicture = findViewById(R.id.profile_picture)
        fullNameEditText = findViewById(R.id.full_name_edittext)
        emailEditText = findViewById(R.id.email_edittext)
        telephoneEditText = findViewById(R.id.telephone_edittext)
        changeEmailLink = findViewById(R.id.change_email_link)


    }
}