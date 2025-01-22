package com.techgaon.myspecialapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class login_screen : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usernameEditText = findViewById<EditText>(R.id.editTextLoginUsername)
        val passwordEditText = findViewById<EditText>(R.id.editTextLoginPassword)
        val loginButton = findViewById<Button>(R.id.buttonLogin)

        val goRegister = findViewById<TextView>(R.id.goRegister)
        goRegister.setOnClickListener {
            startActivity(Intent(this, register_screen::class.java))
        }
        //Initialize AuthHelper
        val authHelper = AuthHelper(this)

        // Handle login
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
            } else {

                val isLoggedIn = authHelper.loginUser(username, password)
                if (isLoggedIn) {
                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, home_screen::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Invalid username or password!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}