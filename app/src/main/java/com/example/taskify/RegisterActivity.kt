package com.example.taskify

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val registerBtn = findViewById<Button>(R.id.registerBtn)

        val db = DatabaseHelper(this)

        registerBtn.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()

            if (user.isNotEmpty() && pass.isNotEmpty()) {
                if (db.registerUser(user, pass)) {
                    Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
            } else {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}