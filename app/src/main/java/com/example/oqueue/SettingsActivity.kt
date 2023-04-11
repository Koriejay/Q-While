package com.example.oqueue

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    private lateinit var backButton: ImageView
    private lateinit var theme: TextView
    private lateinit var tv_backup: TextView
    private lateinit var notif: TextView
    private lateinit var data: TextView
    private lateinit var tv_storage: TextView
    private lateinit var tv_account: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        backButton = findViewById(R.id.back_arrow)
        theme = findViewById(R.id.theme_text)
        tv_backup = findViewById(R.id.backup_text)
        notif = findViewById(R.id.notification_text)
        data = findViewById(R.id.data_usage_text)
        tv_storage = findViewById(R.id.storage_usage_text)
        tv_account = findViewById(R.id.account_settings_text)

        backButton.setOnClickListener {
            finish()
        }

        theme.setOnClickListener {
            startActivity(Intent(this, Theme::class.java))
        }

        tv_backup.setOnClickListener {
            startActivity(Intent(this, backup::class.java))
        }

        notif.setOnClickListener {
            startActivity(Intent(this, SettNotifs::class.java))
        }

        data.setOnClickListener {
            startActivity(Intent(this, dataUsage::class.java))
        }

        tv_storage.setOnClickListener {
            startActivity(Intent(this, storage::class.java))
        }

        tv_account.setOnClickListener {
            startActivity(Intent(this, account::class.java))
        }

    }
}
