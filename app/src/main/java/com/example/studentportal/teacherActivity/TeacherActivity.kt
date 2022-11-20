package com.example.studentportal.teacherActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.studentportal.R

class TeacherActivity : AppCompatActivity() {

    private lateinit var btnTeacherInsertData: Button
    private lateinit var btnTeacherFetchData: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher)

        btnTeacherInsertData = findViewById(R.id.btnTeacherInsertData)
        btnTeacherFetchData = findViewById(R.id.btnTeacherFetchData)

        btnTeacherInsertData.setOnClickListener {
            val intent = Intent(this, TeacherInsertionActivity::class.java)
            startActivity(intent)
        }

        btnTeacherFetchData.setOnClickListener {
            val intent = Intent(this, TeacherFetchingActivity::class.java)
            startActivity(intent)
        }
    }
}