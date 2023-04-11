package com.example.oqueue

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.oqueue.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // mount the bottom nav for activity main
        replaceFragment(HomeFragment())
        binding.nav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.qr -> replaceFragment(QRFragment())
                R.id.profile ->{
                    replaceFragment(ProfileFragment())
                    hideNotificationBadge()
                }
                else ->{
                }
            }
            true
        }
        binding.nav.selectedItemId = R.id.home

        // Show notification badge on profile item if there are new services
        val dbRef = FirebaseDatabase.getInstance().getReference("services")
        dbRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val badgeView = binding.nav.getOrCreateBadge(R.id.profile)
                badgeView.isVisible = true
                badgeView.number += 1
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                // not used in this implementation
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                // not used in this implementation
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // not used in this implementation
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Notifications Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }

    private fun hideNotificationBadge() {
        val badgeView = binding.nav.getBadge(R.id.profile)
        if (badgeView != null && badgeView.isVisible) {
            badgeView.isVisible = false
            badgeView.number = 0
        }
    }
    fun setMenuEnabled(enabled: Boolean) {
        val navigationView = findViewById<BottomNavigationView>(R.id.nav)
        val menu = navigationView.menu
        for (i in 0 until menu.size()) {
            menu.getItem(i).isEnabled = enabled
        }
    }
}