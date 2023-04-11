package com.example.oqueue

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SettNotifs : AppCompatActivity() {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switch: Switch
    private lateinit var tonesSpinner: Spinner
    private lateinit var saveButton: Button
    private lateinit var selectedTone: String
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sett_notifs)

        // Initialize UI components
        switch = findViewById(R.id.notificationsSwitch)
        tonesSpinner = findViewById(R.id.tv_tones)
        saveButton = findViewById(R.id.btn_save)
        backButton = findViewById(R.id.back_arrow)
        backButton.setOnClickListener {
            finish()
        }

        val tones = arrayListOf(
            "Adaptive Sound",
            "Ambience",
            "Aurora",
            "Bounce",
            "Calypso",
            "Chord",
            "Clip",
            "Crystals",
            "Drip",
            "Drop",
            "Glitter",
            "Knock-Knock",
            "Laser",
            "Notice",
            "Over the Horizon",
            "Percussion",
            "Prism",
            "Simple Bell",
            "Sprinkle",
            "Sunny"
        )
        tonesSpinner.adapter =
            ArrayAdapter(applicationContext, android.R.layout.simple_spinner_dropdown_item, tones)


        tonesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedTone = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        saveButton.setOnClickListener {
            Toast.makeText(applicationContext, "Notification settings changed", Toast.LENGTH_LONG)
                .show()
        }

        selectedTone = tonesSpinner.selectedItem.toString()
        val toneIndex = tones.indexOf(selectedTone)
        if (toneIndex != -1) {
            tonesSpinner.setSelection(toneIndex)
        }
    }
}