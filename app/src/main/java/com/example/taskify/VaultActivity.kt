package com.example.taskify

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class VaultActivity : AppCompatActivity() {

    private lateinit var fileContainer: LinearLayout
    private lateinit var db: DatabaseHelper

    private val PICK_FILE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vault)

        val passwordInput = findViewById<EditText>(R.id.passwordInput)
        val unlockBtn = findViewById<Button>(R.id.unlockBtn)
        val vaultContent = findViewById<LinearLayout>(R.id.vaultContent)
        val addFileBtn = findViewById<Button>(R.id.addFileBtn)

        fileContainer = findViewById(R.id.fileContainer)
        db = DatabaseHelper(this)

        unlockBtn.setOnClickListener {
            if (passwordInput.text.toString() == "1234") {
                vaultContent.visibility = LinearLayout.VISIBLE
                loadFiles()
                Toast.makeText(this, "Unlocked 🔓", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Wrong Password ❌", Toast.LENGTH_SHORT).show()
            }
        }

        addFileBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            startActivityForResult(intent, PICK_FILE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_FILE && resultCode == Activity.RESULT_OK) {
            val uri: Uri? = data?.data

            if (uri != null) {
                val filePath = uri.toString()
                db.insertFile(filePath)
                addFileView(filePath)
            }
        }
    }

    private fun loadFiles() {
        fileContainer.removeAllViews()
        val list = db.getFiles()

        for (file in list) {
            addFileView(file)
        }
    }

    private fun addFileView(fileName: String) {

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.HORIZONTAL

        val textView = TextView(this)
        textView.text = fileName
        textView.setTextColor(resources.getColor(android.R.color.white))
        textView.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)

        val deleteBtn = Button(this)
        deleteBtn.text = "Remove"

        deleteBtn.setOnClickListener {
            db.deleteFile(fileName)
            fileContainer.removeView(layout)
        }

        layout.addView(textView)
        layout.addView(deleteBtn)

        fileContainer.addView(layout)
    }
}