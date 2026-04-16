package com.example.taskify

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File

class CameraActivity : AppCompatActivity() {

    private val REQUEST_IMAGE = 1
    private val imageList = ArrayList<Bitmap>()
    private var pdfFile: File? = null

    private lateinit var imageContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        val captureBtn = findViewById<Button>(R.id.captureBtn)
        val doneBtn = findViewById<Button>(R.id.doneBtn)
        val shareBtn = findViewById<Button>(R.id.shareBtn)

        imageContainer = findViewById(R.id.imageContainer)

        // 🔥 CAMERA PERMISSION
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                100
            )
        }

        // 📷 Capture (SAFE VERSION)
        captureBtn.setOnClickListener {

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, REQUEST_IMAGE)
            } else {
                Toast.makeText(this, "Camera not available, using demo image", Toast.LENGTH_SHORT).show()

                // 🔥 FALLBACK DEMO IMAGE (NO CRASH GUARANTEE)
                val bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)
                imageList.add(bitmap)
                addImagePreview(bitmap)
            }
        }

        // 📄 Convert to PDF
        doneBtn.setOnClickListener {

            if (imageList.isEmpty()) {
                Toast.makeText(this, "Capture at least 1 image", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            pdfFile = PdfUtils.createPdf(this, imageList)

            Toast.makeText(this, "PDF Created!", Toast.LENGTH_SHORT).show()
        }

        // 📤 Share
        shareBtn.setOnClickListener {

            if (pdfFile == null) {
                Toast.makeText(this, "Create PDF first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val uri = FileProvider.getUriForFile(
                this,
                "${packageName}.fileprovider",
                pdfFile!!
            )

            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "application/pdf"
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            startActivity(Intent.createChooser(intent, "Share PDF"))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK) {

            val photo = data?.extras?.get("data") as? Bitmap

            if (photo != null) {
                imageList.add(photo)
                addImagePreview(photo)
            } else {
                Toast.makeText(this, "Capture failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 🖼️ PREVIEW
    private fun addImagePreview(bitmap: Bitmap) {

        val imageView = ImageView(this)
        imageView.setImageBitmap(bitmap)

        val params = LinearLayout.LayoutParams(300, 300)
        params.setMargins(10, 10, 10, 10)
        imageView.layoutParams = params

        imageView.scaleType = ImageView.ScaleType.CENTER_CROP

        imageContainer.addView(imageView)
    }
}