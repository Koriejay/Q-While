package com.example.oqueue

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class account : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var backButton: ImageView
    private lateinit var privacySettings: TextView
    private lateinit var connectedAccounts: TextView
    private lateinit var subscriptions: TextView
    private lateinit var delete: Button
    private lateinit var profile: TextView
    private lateinit var logout: TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        auth = FirebaseAuth.getInstance()

        backButton = findViewById(R.id.back_arrow)
        privacySettings = findViewById(R.id.privacy_settings)
        connectedAccounts = findViewById(R.id.connected_accounts)
        subscriptions = findViewById(R.id.subscription_management)
        delete = findViewById(R.id.delete_account_button)
        profile = findViewById(R.id.edt_prof)
        logout = findViewById(R.id.logout)

        backButton.setOnClickListener {
            finish()
        }
        profile.setOnClickListener {
            startActivity(Intent(this, EdtProfile::class.java))
        }
        logout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, Login::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }
        privacySettings.setOnClickListener {
            Toast.makeText(applicationContext, "Privacy Settings have already been declared in phone settings", Toast.LENGTH_LONG).show()
        }
        connectedAccounts.setOnClickListener {
            Toast.makeText(applicationContext, "No connected accounts", Toast.LENGTH_LONG).show()
        }
        subscriptions.setOnClickListener {
            Toast.makeText(applicationContext, "No subscriptions made", Toast.LENGTH_LONG).show()
        }
        delete.setOnClickListener {
            // delete account
            // show a confirmation dialog box
            Toast.makeText(applicationContext, "Account Deleted", Toast.LENGTH_LONG).show()
        }
    }
}