package com.example.oqueue

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Registration : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    private lateinit var myDb: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        myDb = FirebaseDatabase.getInstance().getReference("users")

        findViewById<Button>(R.id.btn_reg).setOnClickListener { view ->
            register()
        }
    }

    private fun register() {
        val firstname = findViewById<View>(R.id.fname) as EditText
        val lastname = findViewById<View>(R.id.lname) as EditText
        val emailtxt = findViewById<View>(R.id.reg_email) as EditText
        val tel_no = findViewById<View>(R.id.tel_no) as EditText
        val passtxt = findViewById<View>(R.id.reg_pass) as EditText

        val first_name = firstname.text.toString()
        val last_name = lastname.text.toString()
        val email = emailtxt.text.toString()
        val telephone = tel_no.text.toString()
        val password = passtxt.text.toString()

        if (first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() || telephone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    val userId = user?.uid.toString()
                    myDb.child(userId).child("first_name").setValue(first_name)
                    myDb.child(userId).child("last_name").setValue(last_name)
                    myDb.child(userId).child("email").setValue(email)
                    myDb.child(userId).child("telephone").setValue(telephone)
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    Toast.makeText(this, "Registration failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

}