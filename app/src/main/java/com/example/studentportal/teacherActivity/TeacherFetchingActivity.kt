package com.example.studentportal.teacherActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentportal.R
import com.example.studentportal.adapters.TeacherAdapter
import com.example.studentportal.models.TeacherModel
import com.google.firebase.database.*

class TeacherFetchingActivity : AppCompatActivity() {

    private lateinit var teRecyclerView: RecyclerView
    private lateinit var tvTeacherLoadingData: TextView
    private lateinit var teacherList: ArrayList<TeacherModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_fetching)

        teRecyclerView = findViewById(R.id.rvTe)
        teRecyclerView.layoutManager = LinearLayoutManager(this)
        teRecyclerView.setHasFixedSize(true)
        tvTeacherLoadingData = findViewById(R.id.tvTeacherLoadingData)

        teacherList = arrayListOf<TeacherModel>()

        getTeacherData()
    }

    private fun getTeacherData() {

        teRecyclerView.visibility = View.GONE
        tvTeacherLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("List of teacher")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                teacherList.clear()
                if (snapshot.exists()) {
                    for (teSnap in snapshot.children) {
                        val teData = teSnap.getValue(TeacherModel::class.java)
                        teacherList.add(teData!!)
                    }
                    val mAdapter = TeacherAdapter(teacherList)
                    teRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : TeacherAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@TeacherFetchingActivity, TeacherDetailsActivity::class.java)
                            intent.putExtra("teId", teacherList[position].teId)
                            intent.putExtra("teName", teacherList[position].teName)
                            intent.putExtra("teLesson", teacherList[position].teLesson)
                            startActivity(intent)
                        }
                    })
                    teRecyclerView.visibility = View.VISIBLE
                    tvTeacherLoadingData.visibility = View.GONE
                }
                tvTeacherLoadingData.visibility = View.GONE
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}