package com.example.oqueue

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

class Notifications : AppCompatActivity() {
    val qrCodeWriter = QRCodeWriter()
    private lateinit var backButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        val backButton = findViewById<ImageView>(R.id.back_arrow)
        backButton.setOnClickListener {
            finish()
        }

        // Retrieve the data passed from the previous activity
        val serviceId = intent.getIntExtra("service_id", 0)
        val branch = intent.getStringExtra("branch")
        val service = intent.getStringExtra("service")
        val time = intent.getStringExtra("time")

        // Initialize the UI elements in the layout
        val idTextView = findViewById<TextView>(R.id.txt_tkt)
        val serviceTextView = findViewById<TextView>(R.id.txt_service)
        val branchTextView = findViewById<TextView>(R.id.txt_branch)
        val timeTextView = findViewById<TextView>(R.id.textview_time)

        // Set the values of the UI elements based on the data passed from the previous activity
        idTextView.text = "Your ticket number is: ${serviceId.toString()}"
        serviceTextView.text = "Service: ${service.toString()}"
        branchTextView.text = "Branch: ${branch.toString()}"
        timeTextView.text = "Time of service: ${time.toString()}"

        val data = "$serviceId $branch $service $time"
        val size = 512

        try {
            val bitMatrix: BitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, size, size)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }

            // Set the generated QR code bitmap to the ImageView in the layout
            val imageView = findViewById<ImageView>(R.id.imageView)
            imageView.setImageBitmap(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }

    }
}
