package com.example.studentportal.studentActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.studentportal.R
import com.example.studentportal.models.StudentModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class StudentDetailsActivity : AppCompatActivity() {

    private lateinit var tvStId: TextView
    private lateinit var tvStName: TextView
    private lateinit var tvStGroup: TextView
    private lateinit var tvStAge: TextView
    private lateinit var btnStudentUpdate: Button
    private lateinit var btnStudentDelete: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        initView()
        setValuesToViews()

        btnStudentDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("stId").toString()
            )
        }

        btnStudentUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("stId").toString(),
                intent.getStringExtra("stName").toString()
            )
        }
    }

    private fun initView() {
        tvStId = findViewById(R.id.tvStId)
        tvStName = findViewById(R.id.stNameText)
        tvStGroup = findViewById(R.id.tvStGroup)
        tvStAge = findViewById(R.id.tvStAge)

        btnStudentUpdate = findViewById(R.id.btnStudentUpdate)
        btnStudentDelete = findViewById(R.id.btnStudentDelete)
    }

    private fun setValuesToViews() {
        tvStId.text = intent.getStringExtra("stId")
        tvStName.text = intent.getStringExtra("stName")
        tvStGroup.text = intent.getStringExtra("stGroup")
        tvStAge.text = intent.getStringExtra("stAge")
    }

    private fun openUpdateDialog(stId: String, stName: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.student_update_dialog, null)

        mDialog.setView(mDialogView)

        val etStName = mDialogView.findViewById<EditText>(R.id.upStName)
        val etStGroup = mDialogView.findViewById<EditText>(R.id.upStGroup)
        val etStAge = mDialogView.findViewById<EditText>(R.id.upStAge)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnStudentUpdateData)

        etStName.setText(intent.getStringExtra("stName").toString())
        etStGroup.setText(intent.getStringExtra("stGroup").toString())
        etStAge.setText(intent.getStringExtra("stAge").toString())

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateStData(
                stId,
                etStName.text.toString(),
                etStGroup.text.toString(),
                etStAge.text.toString()
            )

            Toast.makeText(applicationContext, "Данные обновлены", Toast.LENGTH_LONG).show()

            tvStName.text = etStName.text.toString()
            tvStGroup.text = etStGroup.text.toString()
            tvStAge.text = etStAge.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateStData(
        id: String,
        name: String,
        group: String,
        age: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("List of students").child(id)
        val stInfo = StudentModel(id, name, group, age)
        dbRef.setValue(stInfo)
    }

    private fun deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("List of students").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Данные удалены", Toast.LENGTH_LONG).show()
            val intent = Intent(this, StudentFetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Ошибка: ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
}