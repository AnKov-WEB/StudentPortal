package com.example.studentportal.studentActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.studentportal.R

class StudentActivity : AppCompatActivity() {

    private lateinit var btnStudentInsertData: Button
    private lateinit var btnStudentFetchData: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        btnStudentInsertData = findViewById(R.id.btnStudentInsertData)
        btnStudentFetchData = findViewById(R.id.btnStudentFetchData)

        btnStudentInsertData.setOnClickListener {
            val intent = Intent(this, StudentInsertionActivity::class.java)
            startActivity(intent)
        }

        btnStudentFetchData.setOnClickListener {
            val intent = Intent(this, StudentFetchingActivity::class.java)
            startActivity(intent)
        }
    }
}