package com.techgaon.myspecialapp

import StudentsDatabaseHelper
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class home_screen : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var themeStatusTextView: TextView

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPref = getSharedPreferences("app_preferences", MODE_PRIVATE)

        applyTheme()

        setContentView(R.layout.activity_home_screen)

        themeStatusTextView = findViewById(R.id.textViewWelcome)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // sqlite

        val dbHelper = StudentsDatabaseHelper(this)

        //add
        dbHelper.addStudent("Amit", "10th", 85)
        dbHelper.addStudent("Ajay", "BSc", 90)

        //  data fetch
        val students = dbHelper.getAllStudents()
        students.forEach {
            println("ID: ${it["id"]}, Name: ${it["name"]}, Class: ${it["class"]}, Marks: ${it["marks"]}")
        }

        // Kisi student ke marks update karo
        dbHelper.updateMarks(1, 95)

        // Kisi student ka data delete karo
        dbHelper.deleteStudent(1)
        // Initialize AuthHelper
        val authH = AuthHelper(this)
        themeStatusTextView.text = "Welcome! ${authH.getUserName()}"

        // Logout Button
        val logout = findViewById<Button>(R.id.logout)
        logout.setOnClickListener {
            authH.logoutUser()
            startActivity(Intent(this, login_screen::class.java))
            finish()
        }

        // Initialize Bottom Navigation
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> loadFragment(Home_Fragement())
                R.id.nav_explore -> loadFragment(ExploreFragment())
                R.id.nav_profile -> loadFragment(ProfileFragement())
            }
            true
        }

        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(Home_Fragement())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.dark -> {
                setThemeMode(AppCompatDelegate.MODE_NIGHT_YES)
                showFeedback("Dark Mode Enabled")
                true
            }

            R.id.light -> {
                setThemeMode(AppCompatDelegate.MODE_NIGHT_NO)
                showFeedback("Light Mode Enabled")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun applyTheme() {
        val themeMode = sharedPref.getInt("theme_mode", AppCompatDelegate.MODE_NIGHT_NO)
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }

    private fun setThemeMode(mode: Int) {
        val editor = sharedPref.edit()
        editor.putInt("theme_mode", mode)
        editor.apply()
        AppCompatDelegate.setDefaultNightMode(mode)
        recreate()
    }

    private fun showFeedback(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        themeStatusTextView.text = message
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
