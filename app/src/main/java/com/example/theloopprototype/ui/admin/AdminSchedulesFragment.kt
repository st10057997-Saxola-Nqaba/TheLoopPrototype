package com.example.theloopprototype.ui.admin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.theloopprototype.R
import com.example.theloopprototype.adapter.AdminScheduleListAdapter
import com.example.theloopprototype.data.DummyRequests
import com.example.theloopprototype.models.DScheduledRequestList
import com.example.theloopprototype.models.ScheduleStatus
import java.time.LocalDateTime

class AdminSchedulesFragment : Fragment(R.layout.fragment_admin_schedules) {

    private val scheduleLists = mutableListOf<DScheduledRequestList>()
    private lateinit var adapter: AdminScheduleListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize with default dummy list items
        if (scheduleLists.isEmpty()) {
            scheduleLists.addAll(DummyRequests.scheduledRequestLists)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerSchedulesList)
        val btnBack = view.findViewById<Button>(R.id.btnBackFromSchedules)
        val btnCreateSchedule = view.findViewById<Button>(R.id.btnBroadcastNotification) // repurposed or add new button ID
        btnCreateSchedule.text = "Create New Request List"

        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnCreateSchedule.setOnClickListener {
            showEditOrCreateDialog(null)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AdminScheduleListAdapter(scheduleLists) { schedule ->
            showEditOrCreateDialog(schedule)
        }
        recyclerView.adapter = adapter
    }

    private fun showEditOrCreateDialog(existingSchedule: DScheduledRequestList?) {
        val context = requireContext()
        val builder = AlertDialog.Builder(context)
        builder.setTitle(if (existingSchedule == null) "Create Request List" else "Edit Request List")

        val inputArea = EditText(context).apply {
            hint = "Area ID (e.g. area2)"
            setText(existingSchedule?.areaId ?: "")
        }

        builder.setView(inputArea)

        builder.setPositiveButton("Save") { _, _ ->
            val areaText = inputArea.text.toString().trim()
            if (areaText.isNotEmpty()) {
                if (existingSchedule == null) {
                    val newId = "srl_${System.currentTimeMillis()}"
                    val newList = DScheduledRequestList(
                        id = newId,
                        areaId = areaText,
                        adminId = "u8",
                        scheduleDate = LocalDateTime.now().plusDays(3),
                        status = ScheduleStatus.CONFIRMED
                    )
                    scheduleLists.add(newList)
                    Toast.makeText(context, "Request List Created", Toast.LENGTH_SHORT).show()
                } else {
                    val index = scheduleLists.indexOfFirst { it.id == existingSchedule.id }
                    if (index != -1) {
                        scheduleLists[index] = existingSchedule.copy(areaId = areaText)
                        Toast.makeText(context, "Request List Updated", Toast.LENGTH_SHORT).show()
                    }
                }
                adapter.notifyDataSetChanged()
            }
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }
}