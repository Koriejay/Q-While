package com.example.oqueue

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(private val services: MutableList<Service>, private val context: Context ,) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val service = services[position]
        holder.bind(service)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, Notifications::class.java)
            intent.putExtra("ticket_id", service.id)
            intent.putExtra("branch name", service.branch)
            intent.putExtra("type of service", service.service)
            intent.putExtra("time", service.time)
            holder.itemView.context.startActivity(intent)
        }
    }
    override fun getItemCount() = services.size

    fun addService(service: Service) {
        services.add(0,service)
        notifyItemInserted(services.size -1)
    }

    fun updateService(service: Service) {
        val index = services.indexOfFirst {
            it.id == service.id
        }
    }

    fun removeService(service: Service) {
        val index = services.indexOfFirst { it.id == service.id }
        if (index != -1) {
            services.removeAt(index)
            notifyItemRemoved(index)
        }
    }
    fun getServices(): MutableList<Service> {
        return services
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val queueNumberTextView: TextView = itemView.findViewById(R.id.textViewA)
        private val queueIDTextView: TextView = itemView.findViewById(R.id.textViewB)
        fun bind(service: Service) {
            queueNumberTextView.text = "Queue - ${service.branch}"
            queueIDTextView.text = "QueueID: ${service.id}"
        }
    }
}
