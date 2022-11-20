package com.example.studentportal.studentActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.studentportal.R
import com.example.studentportal.models.StudentModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class StudentInsertionActivity : AppCompatActivity() {

    private lateinit var etStName: EditText
    private lateinit var etStGroup: EditText
    private lateinit var etStAge: EditText
    private lateinit var btnStudentSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_insertion)

        etStName = findViewById(R.id.nameStudentInputField)
        etStGroup = findViewById(R.id.groupStudentInputField)
        etStAge = findViewById(R.id.ageStudentInputField)
        btnStudentSaveData = findViewById(R.id.btnStudentSaveData)

        dbRef = FirebaseDatabase.getInstance().getReference("List of students")

        btnStudentSaveData.setOnClickListener {
            saveListData()
        }
    }

    private fun saveListData() {
        val stName = etStName.text.toString()
        val stGroup = etStGroup.text.toString()
        val stAge = etStAge.text.toString()

        if(stName.isEmpty()) {
            etStName.error = "Введите имя"
        }

        if(stGroup.isEmpty()) {
            etStGroup.error = "Введите группу"
        }

        if(stAge.isEmpty()) {
            etStAge.error = "Введите возраст"
        }

        val stId = dbRef.push().key!!
        val student = StudentModel(stId, stName, stGroup, stAge)

        if (stName.isNotEmpty() && stGroup.isNotEmpty() && stAge.isNotEmpty()) {
            dbRef.child(stId).setValue(student).addOnCompleteListener {
                etStName.text.clear()
                etStGroup.text.clear()
                etStAge.text.clear()
                Toast.makeText(this, "Данные успешно сохранены", Toast.LENGTH_LONG).show()
            }.addOnFailureListener { error ->
                Toast.makeText(this, "Ошибка: ${error.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}