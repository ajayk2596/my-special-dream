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

class register_screen : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val gologin = findViewById<TextView>(R.id.goLogin)

        gologin.setOnClickListener {
            startActivity(Intent(this, login_screen::class.java))
        }
        // Initialize views
        val usernameEditText = findViewById<EditText>(R.id.editTextRegisterUsername)
        val passwordEditText = findViewById<EditText>(R.id.editTextRegisterPassword)
        val confirmPasswordEditText = findViewById<EditText>(R.id.editTextConfirmPassword)
        val registerButton = findViewById<Button>(R.id.buttonRegister)

        // Initialize AuthHelper
        val authHelper = AuthHelper(this)

        // Handle registration
        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
            } else {

                val isRegistered = authHelper.registerUser(username, password)
                if (isRegistered) {
                    Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, home_screen::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "User already exists!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}