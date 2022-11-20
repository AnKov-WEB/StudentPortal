package com.example.studentportal.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentportal.R
import com.example.studentportal.models.StudentModel

class StudentApadter(private val studentList: ArrayList<StudentModel>):
    RecyclerView.Adapter<StudentApadter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int) {  }
    }

    fun setOnItemClickListener(clickListener: onItemClickListener) {
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.student_list_item, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentSt = studentList[position]
        holder.stNameText.text = currentSt.stName
    }

    override fun getItemCount(): Int {
        return studentList.size
    }

    class ViewHolder(itemView: View, clickListener: onItemClickListener):
        RecyclerView.ViewHolder(itemView) {
        val stNameText: TextView = itemView.findViewById(R.id.tvStName)

        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}