package com.example.oqueue

import android.annotation.SuppressLint
import android.net.TrafficStats
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class dataUsage : AppCompatActivity() {
    private lateinit var backButton: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_usage)

        backButton = findViewById(R.id.back_arrow)
        backButton.setOnClickListener {
            finish()
        }

        // Retrieve data usage metrics
        val totalUsage = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes()
        val backgroundUsage = TrafficStats.getUidRxBytes(android.os.Process.myUid()) +
                TrafficStats.getUidTxBytes(android.os.Process.myUid()) - totalUsage
        val cellularUsage = TrafficStats.getMobileRxBytes() + TrafficStats.getMobileTxBytes()
        val wifiUsage = totalUsage - cellularUsage - backgroundUsage

        // Bind metrics to UI elements
        findViewById<TextView>(R.id.total_data_usage).text = "Total Data Usage: ${formatBytes(totalUsage)}"
        findViewById<TextView>(R.id.background_data_usage).text = "Background Data Usage: ${formatBytes(backgroundUsage)}"
        findViewById<TextView>(R.id.cellular_data_usage).text = "Cellular Data Usage: ${formatBytes(cellularUsage)}"
        findViewById<TextView>(R.id.wifi_data_usage).text = "Wi-Fi Data Usage: ${formatBytes(wifiUsage)}"

    }

    private fun formatBytes(bytes: Long): String {
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        var remainingBytes = bytes.toDouble()
        var index = 0
        while (remainingBytes >= 1024 && index < units.size - 1) {
            remainingBytes /= 1024
            index++
        }
        return String.format("%.2f %s", remainingBytes, units[index])
    }
}