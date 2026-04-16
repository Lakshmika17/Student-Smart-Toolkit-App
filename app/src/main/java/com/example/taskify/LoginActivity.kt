package com.example.taskify

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val registerText = findViewById<TextView>(R.id.registerText)

        val db = DatabaseHelper(this)

        loginBtn.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()

            if (db.loginUser(user, pass)) {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DashboardActivity::class.java))
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }

        registerText.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}