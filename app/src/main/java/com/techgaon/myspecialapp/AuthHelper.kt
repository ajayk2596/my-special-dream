package com.techgaon.myspecialapp

import android.content.Context
import android.content.Context.MODE_PRIVATE

class AuthHelper(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("userdata", MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    // Register user
    fun registerUser(name: String, password: String): Boolean {
//        if (sharedPreferences.contains("name")) {
//            return false
//        }
//       else{
        editor.putString("username", name)
        editor.putString("password", password)
        editor.putBoolean("is_logged_in", false)
        editor.apply()
        return true
        //}
    }

    // Login user
    fun loginUser(name: String, password: String): Boolean {
        val storedName = sharedPreferences.getString("username", null)
        val storedPassword = sharedPreferences.getString("password", null)
        return if (storedName == name && storedPassword == password) {
            editor.putBoolean("is_logged_in", true)
            editor.apply()
            true
        } else {
            false
        }
    }

    // Logout user
    fun logoutUser() {
        editor.putBoolean("is_logged_in", false)
        editor.apply()
    }

    // check Login User
    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("is_logged_in", false)
    }

    // Fetch Name User
    fun getUserName(): String? {
        return sharedPreferences.getString("username", null)
    }

    fun clearUserData() {
        editor.clear()
        editor.apply()
    }
}
