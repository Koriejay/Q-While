package com.example.oqueue

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    private lateinit var branchSpinner: Spinner
    private lateinit var serviceSpinner: Spinner
    private lateinit var timeSpinner: Spinner
    private lateinit var submitButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        branchSpinner = view.findViewById(R.id.sp_branch)
        serviceSpinner = view.findViewById(R.id.sp_service)
        timeSpinner = view.findViewById(R.id.sp_time)
        submitButton = view.findViewById(R.id.btn_submit)

        val branchesSp = arrayListOf(" ", "Nairobi", "Mombasa", "Kisumu", "Nakuru", "Eldoret", "Embu", "Machakos", "Meru", "Kiambu", "Garissa", "Wajir", "Kericho", "Bomet", "Kakamega", "Vihiga",
            "Kitui", "Marsabit", "Lamu", "Isiolo", "Narok", "Kajiado", "Taita Taveta", "Tharaka Nithi", "Migori", "Homa Bay", "Siaya", "Bungoma", "Busia", "Nyandarua", "Nyeri", "Kirinyaga", "Muranga", "Laikipia", "Trans Nzoia", "West Pokot",
            "Turkana", "Samburu", "Uasin Gishu", "Elgeyo Marakwet", "Nandi", "Kisii", "Nyamira", "Makueni", "Tana River", "Kilifi", "Kwale", "Mandera")
        val servicesSp = arrayListOf(" ", "Mail/Letters", "Passport Application", "Passport Collection", "Money Order", "Stamps/Postcards", "Large Parcel", "Small Parcel")
        val timeSp = arrayListOf(" ", "8am - 9am", "9am - 10am", "10am - 11am", "11am - 12:30pm", "2pm - 3pm", "3pm - 4pm", "4pm - 5:30pm")

        branchSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, branchesSp)
        serviceSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, servicesSp)
        timeSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, timeSp)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar = view.findViewById<ProgressBar>(R.id.progress_bar)

        submitButton.setOnClickListener {
            val selectedBranch = branchSpinner.selectedItem.toString().trim()
            val selectedService = serviceSpinner.selectedItem.toString().trim()
            val selectedTime = timeSpinner.selectedItem.toString().trim()

            if (selectedService.isEmpty() || selectedBranch.isEmpty() || selectedTime.isEmpty()){
                Toast.makeText(context, "Please select all the fields", Toast.LENGTH_LONG).show()
            }
            else{
                // show progress bar to indicate that entry is being processed
                progressBar.visibility = View.VISIBLE

                // create a Service object with the selected data
                val service = Service(branch = selectedBranch, service = selectedService, time = selectedTime)

                // add the Service object to the database
                FirebaseHandler().addService(service,
                    onSuccess = {
                        // hide progress bar when entry is complete
                        progressBar.visibility = View.GONE

                        // display the dialog box
                        showQueueCreatedDialog()

                        branchSpinner.setSelection(0)
                        serviceSpinner.setSelection(0)
                        timeSpinner.setSelection(0)
                    },
                    onError = { errorMessage ->
                        // hide progress bar when an error occurs
                        progressBar.visibility = View.GONE

                        // display an error message
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    })
            }
        }
    }
    private fun showQueueCreatedDialog() {
        // Disable other menu items
        (activity as MainActivity).setMenuEnabled(false)

        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog)
        dialog.setOnDismissListener {
            // Enable other menu items when dialog is dismissed
            (activity as MainActivity).setMenuEnabled(true)
        }
        dialog.findViewById<Button>(R.id.btn_close).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}

