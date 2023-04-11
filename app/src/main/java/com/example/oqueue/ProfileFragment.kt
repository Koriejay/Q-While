package com.example.oqueue

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private var numNewServices: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("services")

        val listView = view.findViewById<ListView>(R.id.ltv)
        val items = listOf("My Queues", "Settings", "About QiT")

        val adapter = object : BaseAdapter() {
            override fun getCount(): Int = items.size

            override fun getItem(position: Int): String = items[position]

            override fun getItemId(position: Int): Long = position.toLong()

            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val view = convertView ?: LayoutInflater.from(parent?.context).inflate(android.R.layout.simple_list_item_1, parent, false)
                view.findViewById<TextView>(android.R.id.text1).text = items[position]
                return view
            }
        }

        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            when (adapter.getItem(position)) {
                "Settings" -> startActivity(Intent(context, SettingsActivity::class.java))
                "About QiT" -> startActivity(Intent(context, AboutUs::class.java))
                "My Queues" -> {
                    // Hide notification badge and reset new services counter
                    val badgeView = view.findViewById<TextView>(R.id.badge)
                    badgeView.visibility = View.GONE
                    numNewServices = 0
                    startActivity(Intent(context, History::class.java))
                }
            }
        }

        dbRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                if (snapshot.key != null && !snapshot.key!!.startsWith("_")) {
                    numNewServices++
                    val badgeView = view.findViewById<TextView>(R.id.badge)
                    badgeView.visibility = View.VISIBLE
                    badgeView.text = 1.toString()
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                // Do nothing
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                // Do nothing
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                // Do nothing
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Notifications Error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}