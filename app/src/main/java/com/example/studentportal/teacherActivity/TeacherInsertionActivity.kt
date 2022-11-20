package com.example.studentportal.teacherActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.studentportal.R
import com.example.studentportal.models.TeacherModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TeacherInsertionActivity : AppCompatActivity() {

    private lateinit var etTeName: EditText
    private lateinit var etTeLesson: EditText
    private lateinit var btnTeacherSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_insertion)

        etTeName = findViewById(R.id.nameTeacherInputField)
        etTeLesson = findViewById(R.id.lessonTeacherInputField)
        btnTeacherSaveData = findViewById(R.id.btnTeacherSaveData)

        dbRef = FirebaseDatabase.getInstance().getReference("List of teacher")

        btnTeacherSaveData.setOnClickListener {
            saveListData()
        }
    }

    private fun saveListData() {
        val teName = etTeName.text.toString()
        val teLesson = etTeLesson.text.toString()

        if(teName.isEmpty()) {
            etTeName.error = "Введите имя"
        }

        if(teLesson.isEmpty()) {
            etTeLesson.error = "Введите предмет"
        }

        val teId = dbRef.push().key!!
        val teacher = TeacherModel(teId, teName, teLesson)

        if (teName.isNotEmpty() && teLesson.isNotEmpty()) {
            dbRef.child(teId).setValue(teacher).addOnCompleteListener {
                etTeName.text.clear()
                etTeLesson.text.clear()
                Toast.makeText(this, "Данные успешно сохранены", Toast.LENGTH_LONG).show()
            }.addOnFailureListener { error ->
                Toast.makeText(this, "Ошибка: ${error.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}