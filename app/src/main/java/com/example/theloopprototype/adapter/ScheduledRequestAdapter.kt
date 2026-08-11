package com.example.theloopprototype.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.theloopprototype.R
import com.example.theloopprototype.models.DScheduledRequestList
import com.example.theloopprototype.models.DArea
import java.time.format.DateTimeFormatter
import java.util.Collections

class ScheduledRequestAdapter(
    private val requestList: MutableList<DScheduledRequestList>,
    private val areas: List<DArea>
) : RecyclerView.Adapter<ScheduledRequestAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAreaName: TextView = view.findViewById(R.id.tvAreaName)
        val tvScheduleDate: TextView = view.findViewById(R.id.tvScheduleDate)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_scheduled_request, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val schedule = requestList[position]

        val matchedArea = areas.find { it.id == schedule.areaId }?.name ?: "Unknown Area"
        holder.tvAreaName.text = matchedArea

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val formattedDate = schedule.scheduleDate.format(formatter)

        holder.tvScheduleDate.text = "Date: $formattedDate"
        holder.tvStatus.text = "Status: ${schedule.status}"
    }

    override fun getItemCount(): Int = requestList.size

    // Standard swap logic that allows continuous movement back and forth
    fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(requestList, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(requestList, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }
}