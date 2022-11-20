package com.example.studentportal.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.studentportal.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)

        val handler = Handler()
        handler.postDelayed({ start() }, 2000)
    }

    private fun start() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}