package com.example.theloopprototype.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.theloopprototype.R
import com.example.theloopprototype.models.DScheduledRequestList
import java.time.format.DateTimeFormatter

class AdminScheduleListAdapter(
    private val schedules: List<DScheduledRequestList>,
    private val onEditClick: (DScheduledRequestList) -> Unit
) : RecyclerView.Adapter<AdminScheduleListAdapter.ScheduleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin_schedule, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(schedules[position], onEditClick)
    }

    override fun getItemCount(): Int = schedules.size

    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvTitle: TextView = itemView.findViewById(R.id.tvScheduleTitle)
        private val tvDetails: TextView = itemView.findViewById(R.id.tvScheduleDetails)
        private val btnEdit: TextView = itemView.findViewById(R.id.btnEditSchedule)

        fun bind(schedule: DScheduledRequestList, onEditClick: (DScheduledRequestList) -> Unit) {
            tvTitle.text = "Schedule List: ${schedule.id} | Area: ${schedule.areaId}"
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
            tvDetails.text = "Date: ${schedule.scheduleDate.format(formatter)}\nStatus: ${schedule.status}"

            btnEdit.setOnClickListener { onEditClick(schedule) }
        }
    }
}