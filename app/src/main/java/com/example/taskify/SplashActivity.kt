package com.example.taskify

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<ImageView>(R.id.logo)
        val text = findViewById<TextView>(R.id.appName)

        // 🔥 Fade + Scale Animation
        logo.scaleX = 0f
        logo.scaleY = 0f
        logo.animate().scaleX(1f).scaleY(1f).setDuration(1200).start()

        text.alpha = 0f
        text.animate().alpha(1f).setDuration(1500).start()

        // ⏳ Delay → Login Screen
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2500)
    }
}