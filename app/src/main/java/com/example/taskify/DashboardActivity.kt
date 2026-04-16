package com.example.taskify

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val taskBtn = findViewById<LinearLayout>(R.id.taskBtn)
        val notesBtn = findViewById<LinearLayout>(R.id.notesBtn)
        val cameraBtn = findViewById<LinearLayout>(R.id.cameraBtn)
        val dictionaryBtn = findViewById<LinearLayout>(R.id.dictionaryBtn)
        val vaultBtn = findViewById<LinearLayout>(R.id.vaultBtn)

        taskBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        notesBtn.setOnClickListener {
            startActivity(Intent(this, NotesActivity::class.java))
        }

        cameraBtn.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }

        dictionaryBtn.setOnClickListener {
            startActivity(Intent(this, DictionaryActivity::class.java))
        }

        vaultBtn.setOnClickListener {
            startActivity(Intent(this, VaultActivity::class.java))
        }
    }
}