package com.example.studentportal.teacherActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.studentportal.R
import com.example.studentportal.models.TeacherModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TeacherDetailsActivity : AppCompatActivity() {

    private lateinit var tvTeId: TextView
    private lateinit var tvTeName: TextView
    private lateinit var tvTeLesson: TextView
    private lateinit var btnTeacherUpdate: Button
    private lateinit var btnTeacherDelete: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_details)

        initView()
        setValuesToViews()

        btnTeacherDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("teId").toString()
            )
        }

        btnTeacherUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("teId").toString(),
                intent.getStringExtra("teName").toString()
            )
        }
    }

    private fun initView() {
        tvTeId = findViewById(R.id.tvTeId)
        tvTeName = findViewById(R.id.teNameText)
        tvTeLesson = findViewById(R.id.tvTeLesson)

        btnTeacherUpdate = findViewById(R.id.btnTeacherUpdate)
        btnTeacherDelete = findViewById(R.id.btnTeacherDelete)
    }

    private fun setValuesToViews() {
        tvTeId.text = intent.getStringExtra("teId")
        tvTeName.text = intent.getStringExtra("teName")
        tvTeLesson.text = intent.getStringExtra("teLesson")
    }

    private fun openUpdateDialog(teId: String, teName: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.teacher_update_dialog, null)

        mDialog.setView(mDialogView)

        val etTeName = mDialogView.findViewById<EditText>(R.id.upTeName)
        val etTeLesson = mDialogView.findViewById<EditText>(R.id.upTeLesson)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnTeacherUpdateData)

        etTeName.setText(intent.getStringExtra("teName").toString())
        etTeLesson.setText(intent.getStringExtra("teLesson").toString())

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateTeData(
                teId,
                etTeName.text.toString(),
                etTeLesson.text.toString()
            )

            Toast.makeText(applicationContext, "Данные обновлены", Toast.LENGTH_LONG).show()

            tvTeName.text = etTeName.text.toString()
            tvTeLesson.text = etTeLesson.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateTeData(
        id: String,
        name: String,
        lesson: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("List of teacher").child(id)
        val teInfo = TeacherModel(id, name, lesson)
        dbRef.setValue(teInfo)
    }

    private fun deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("List of teacher").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Данные удалены", Toast.LENGTH_LONG).show()
            val intent = Intent(this, TeacherFetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Ошибка: ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
}