package com.example.studentportal.studentActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentportal.R
import com.example.studentportal.adapters.StudentApadter
import com.example.studentportal.models.StudentModel
import com.google.firebase.database.*

class StudentFetchingActivity : AppCompatActivity() {

    private lateinit var stRecyclerView: RecyclerView
    private lateinit var tvStudentLoadingData: TextView
    private lateinit var studentList: ArrayList<StudentModel>

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_fetching)

        stRecyclerView = findViewById(R.id.rvSt)
        stRecyclerView.layoutManager = LinearLayoutManager(this)
        stRecyclerView.setHasFixedSize(true)
        tvStudentLoadingData = findViewById(R.id.tvStudentLoadingData)

        studentList = arrayListOf<StudentModel>()

        getStudentData()
    }

    private fun getStudentData() {

        stRecyclerView.visibility = View.GONE
        tvStudentLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("List of students")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                studentList.clear()
                if (snapshot.exists()) {
                    for (stSnap in snapshot.children) {
                        val stData = stSnap.getValue(StudentModel::class.java)
                        studentList.add(stData!!)
                    }
                    val mAdapter = StudentApadter(studentList)
                    stRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : StudentApadter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@StudentFetchingActivity, StudentDetailsActivity::class.java)
                            intent.putExtra("stId", studentList[position].stId)
                            intent.putExtra("stName", studentList[position].stName)
                            intent.putExtra("stGroup", studentList[position].stGroup)
                            intent.putExtra("stAge", studentList[position].stAge)
                            startActivity(intent)
                        }
                    })
                    stRecyclerView.visibility = View.VISIBLE
                    tvStudentLoadingData.visibility = View.GONE
                }
                tvStudentLoadingData.visibility = View.GONE
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}