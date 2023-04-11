package com.example.oqueue

import com.google.firebase.database.*

class FirebaseHandler {

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val serviceReference: DatabaseReference = database.reference.child("services")

    fun addService(service: Service, onSuccess: () -> Unit, onError: (String) -> Unit) {
        // Generate a new key for the service
        val key = serviceReference.push().key ?: return onError("Failed to generate key")
        // Set the key as the service's ID
        service.id = key
        // Add the service to the database
        serviceReference.child(key).setValue(service)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e.message ?: "Failed to add user to the database") }
    }

    fun getAllServices(onSuccess: (List<Service>) -> Unit, onError: (String) -> Unit) {
        // Query the services node for all services
        serviceReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val services = mutableListOf<Service>()
                // Loop through the snapshot and map each child to a Service object
                snapshot.children.forEach { serviceSnapshot ->
                    val service = serviceSnapshot.getValue(Service::class.java)
                    service?.let { services.add(it) }
                }
                onSuccess(services)
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error.message)
            }
        })
    }

    fun getServiceById(serviceId: String, onSuccess: (Service?) -> Unit, onError: (String) -> Unit) {
        // Query the services node for the specified service
        serviceReference.child(serviceId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val service = snapshot.getValue(Service::class.java)
                onSuccess(service)
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error.message)
            }
        })
    }

    fun getNumServices(onSuccess: (Long) -> Unit, onError: (String) -> Unit) {
        // Query the services node for the number of services
        serviceReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                onSuccess(snapshot.childrenCount)
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error.message)
            }
        })
    }
}
