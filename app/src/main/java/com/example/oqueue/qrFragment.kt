package com.example.oqueue

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector

class QRFragment : Fragment() {
    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 1
    }

    private lateinit var cameraSource: CameraSource
    private lateinit var detector: BarcodeDetector
    private lateinit var surfaceView: SurfaceView
    private lateinit var textView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_qr, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        surfaceView = view.findViewById(R.id.surfaceView)
        textView = view.findViewById(R.id.textView)
        view.findViewById<ProgressBar>(R.id.progress_bar)
        setupCameraSource()
    }

    private fun setupCameraSource() {
        detector = BarcodeDetector.Builder(requireContext())
            .setBarcodeFormats(Barcode.QR_CODE)
            .build()

        cameraSource = CameraSource.Builder(requireContext(), detector)
            .setAutoFocusEnabled(true)
            .setRequestedPreviewSize(1920, 1080)
            .setRequestedFps(15.0f)
            .setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE) // enable continuous autofocus
            .build()

        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    startCameraPreview()
                } else {
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
                }
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })

        detector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val qrCodes = detections.detectedItems
                if (qrCodes.size() != 0) {
                    val code = qrCodes.valueAt(0)
                    if (code != null) {

                        // show progress bar
                        val progressBar = view?.findViewById<ProgressBar>(R.id.progress_bar)
                        progressBar?.visibility = View.VISIBLE

                        // split the data
                        val qrData = code.displayValue.split(";")
                        if (qrData.size == 3) {
                            // pass the data to the service data class
                            val service = Service(
                                branch = qrData[0].trim(),
                                service = qrData[1].trim(),
                                time = qrData[2].trim()
                            )
                            // add the service to the database
                            FirebaseHandler().addService(service,
                                onSuccess = {
                                    // disable progress bar
                                    progressBar?.visibility = View.GONE

                                    // show the dialog box
                                    showQueueCreatedDialog()

                                },
                                onError = { message ->
                                    Toast.makeText(context, "Error while adding to queue", Toast.LENGTH_SHORT).show()
                                }
                            )
                        }
                    }
                }
            }
        })
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

    private fun startCameraPreview() {
        try {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            cameraSource.start(surfaceView.holder)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCameraPreview()
        }
    }
}
