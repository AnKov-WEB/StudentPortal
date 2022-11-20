package com.example.studentportal.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.studentportal.R
import com.example.studentportal.studentActivity.StudentActivity
import com.example.studentportal.teacherActivity.TeacherActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnStudent: Button
    private lateinit var btnTeacher: Button
    private lateinit var btnTable: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStudent = findViewById(R.id.btnStudentList)
        btnTeacher = findViewById(R.id.btnTeacherList)
        btnTable = findViewById(R.id.btnTableList)

        btnStudent.setOnClickListener {
            val intent = Intent(this, StudentActivity::class.java)
            startActivity(intent)
        }

        btnTeacher.setOnClickListener {
            val intent = Intent(this, TeacherActivity::class.java)
            startActivity(intent)
        }

        /*btnTable.setOnClickListener {
            val intent = Intent(this, TableActivity::class.java)
            startActivity(intent)
        }*/
    }
}