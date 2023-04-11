package com.example.oqueue

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.itextpdf.text.Chunk
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream

class History : AppCompatActivity() {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var backButton: ImageView
    private lateinit var histoBtn: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        backButton = findViewById(R.id.back_arrow)
        backButton.setOnClickListener {
            finish()
        }

        histoBtn = findViewById(R.id.btn_histo)
        histoBtn.setOnClickListener {
            if (hasStoragePermission()) {
                generatePdfFile()
            } else {
                requestStoragePermission()
            }
        }

//            val services = (recyclerView.adapter as? HistoryAdapter)?.getServices() ?: mutableListOf()
//            val fileName = "queue_history.pdf"
//            val file = File(getExternalFilesDir(null), fileName)
//            val document = Document()
//            try {
//                PdfWriter.getInstance(document, FileOutputStream(file))
//                document.open()
//                for (service in services) {
//                    document.add(Paragraph("Type of service: ${service.service}"))
//                    document.add(Paragraph("Branch: ${service.branch}"))
//                    document.add(Paragraph("Service Time: ${service.time}"))
//                    document.add(Chunk.NEWLINE)
//                }
//                Toast.makeText(applicationContext,"$fileName downloaded", Toast.LENGTH_LONG).show()
//            } catch (e: Exception) {
//                e.printStackTrace()
//                Toast.makeText(applicationContext,"Error while generating PDF",Toast.LENGTH_SHORT).show()
//            } finally {
//                document.close()
//            }
//        }

        recyclerView = findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = HistoryAdapter(mutableListOf(), this)

        val database = FirebaseDatabase.getInstance().getReference("services")
        database.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val service = dataSnapshot.getValue(Service::class.java)
                service?.let {
                    (recyclerView.adapter as? HistoryAdapter)?.addService(it)
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val service = dataSnapshot.getValue(Service::class.java)
                service?.let {
                    (recyclerView.adapter as? HistoryAdapter)?.updateService(it)
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                val service = dataSnapshot.getValue(Service::class.java)
                service?.let {
                    (recyclerView.adapter as? HistoryAdapter)?.removeService(it)
                }
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
                // Nothing
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(applicationContext,"Fatal error during retrieval of data",Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun hasStoragePermission(): Boolean {
        return true
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//        } else {
//            true
//        }
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            generatePdfFile()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun generatePdfFile() {
        val services = (recyclerView.adapter as? HistoryAdapter)?.getServices() ?: mutableListOf()
        val fileName = "queue_history.pdf"
        val file = File(getExternalFilesDir(null), fileName)
        val document = Document()
        try {
            PdfWriter.getInstance(document, FileOutputStream(file))
            document.open()
            for (service in services) {
                document.add(Paragraph("Type of service: ${service.service}"))
                document.add(Paragraph("Branch: ${service.branch}"))
                document.add(Paragraph("Service Time: ${service.time}"))
                document.add(Chunk.NEWLINE)
            }
            Toast.makeText(applicationContext,"$fileName downloaded", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext,"Error while generating PDF",Toast.LENGTH_SHORT).show()
        } finally {
            document.close()
        }
    }
}

