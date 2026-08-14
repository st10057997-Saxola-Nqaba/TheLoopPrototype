package com.example.theloopprototype.ui.admin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AdminSchedulesFragment : Fragment(R.layout.fragment_admin_schedules) {

    private val scheduleLists = mutableListOf<DScheduledRequestList>()
    private lateinit var adapter: AdminScheduleListAdapter
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Always sync with the latest list source of truth
        scheduleLists.clear()
        scheduleLists.addAll(DummyRequests.scheduledRequestLists)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerSchedulesList)
        val btnBack = view.findViewById<Button>(R.id.btnBackFromSchedules)
        val btnBroadcast = view.findViewById<Button>(R.id.btnBroadcastNotification)
        val btnCreateSchedule = view.findViewById<Button>(R.id.btnCreateRequestList)

        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnBroadcast.setOnClickListener {
            showBroadcastDialog()
        }

        btnCreateSchedule.setOnClickListener {
            showEditOrCreateDialog(null)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AdminScheduleListAdapter(scheduleLists) { schedule ->
            showOptionsDialog(schedule)
        }
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        // Keep hidden when coming back from dialogs or interactions
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.GONE
    }

    private fun showBroadcastDialog() {
        val context = requireContext()
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 10)
        }

        val inputTarget = EditText(context).apply {
            hint = "Target (Area ID, Owner ID, Group Name)"
        }
        layout.addView(inputTarget)

        val inputMessage = EditText(context).apply {
            hint = "Notification Message..."
            minLines = 3
        }
        layout.addView(inputMessage)

        AlertDialog.Builder(context)
            .setTitle("Send Targeted Broadcast")
            .setView(layout)
            .setPositiveButton("Send") { _, _ ->
                val target = inputTarget.text.toString().trim()
                val message = inputMessage.text.toString().trim()

                if (target.isNotEmpty() && message.isNotEmpty()) {
                    Toast.makeText(context, "Broadcast sent to $target successfully!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Target and message cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showOptionsDialog(schedule: DScheduledRequestList) {
        val options = arrayOf("Edit Schedule", "Delete Schedule")
        AlertDialog.Builder(requireContext())
            .setTitle("Manage Schedule (${schedule.id})")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showEditOrCreateDialog(schedule)
                    1 -> confirmAndDeleteSchedule(schedule)
                }
            }
            .show()
    }

    private fun confirmAndDeleteSchedule(schedule: DScheduledRequestList) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm Deletion")
            .setMessage("Are you sure you want to delete schedule list ${schedule.id}?")
            .setPositiveButton("Delete") { _, _ ->
                DummyRequests.scheduledRequestLists.remove(schedule)
                scheduleLists.remove(schedule)
                adapter.notifyDataSetChanged()
                Toast.makeText(requireContext(), "Schedule deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditOrCreateDialog(existingSchedule: DScheduledRequestList?) {
        val context = requireContext()
        val builder = AlertDialog.Builder(context)
        builder.setTitle(if (existingSchedule == null) "Create Request List" else "Edit Request List")

        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 10)
        }

        val inputArea = EditText(context).apply {
            hint = "Area ID (e.g. area2)"
            setText(existingSchedule?.areaId ?: "")
        }
        layout.addView(inputArea)

        val inputOwner = EditText(context).apply {
            hint = "Owner ID (Optional, e.g. u1)"
        }
        layout.addView(inputOwner)

        val inputPet = EditText(context).apply {
            hint = "Pet ID (Optional, e.g. p1)"
        }
        layout.addView(inputPet)

        val inputGroup = EditText(context).apply {
            hint = "Group Name (Optional, e.g. Community Group)"
        }
        layout.addView(inputGroup)

        val inputDate = EditText(context).apply {
            hint = "Schedule Date (yyyy-MM-dd HH:mm)"
            setText(existingSchedule?.scheduleDate?.format(formatter) ?: LocalDateTime.now().plusDays(3).format(formatter))
        }
        layout.addView(inputDate)

        val inputAdmin = EditText(context).apply {
            hint = "Assigned AHT / Admin ID (e.g. u8)"
            setText(existingSchedule?.adminId ?: "u8")
        }
        layout.addView(inputAdmin)

        builder.setView(layout)

        builder.setPositiveButton("Save") { _, _ ->
            val areaText = inputArea.text.toString().trim()
            val ownerText = inputOwner.text.toString().trim()
            val petText = inputPet.text.toString().trim()
            val groupText = inputGroup.text.toString().trim()
            val dateText = inputDate.text.toString().trim()
            val adminText = inputAdmin.text.toString().trim()

            if (areaText.isNotEmpty() || ownerText.isNotEmpty() || groupText.isNotEmpty()) {
                val parsedDate = try {
                    LocalDateTime.parse(dateText, formatter)
                } catch (e: Exception) {
                    LocalDateTime.now().plusDays(3)
                }

                val effectiveArea = if (areaText.isNotEmpty()) areaText else (groupText.ifEmpty { "general_target" })

                if (existingSchedule == null) {
                    val newId = "srl_${System.currentTimeMillis()}"
                    val newList = DScheduledRequestList(
                        id = newId,
                        areaId = effectiveArea,
                        adminId = if (adminText.isNotEmpty()) adminText else "u8",
                        scheduleDate = parsedDate,
                        status = com.example.theloopprototype.models.ScheduleStatus.CONFIRMED
                    )
                    DummyRequests.scheduledRequestLists.add(newList)
                    scheduleLists.add(newList)
                    Toast.makeText(context, "Request List Created", Toast.LENGTH_SHORT).show()
                } else {
                    val index = scheduleLists.indexOfFirst { it.id == existingSchedule.id }
                    if (index != -1) {
                        val updatedList = existingSchedule.copy(
                            areaId = effectiveArea,
                            scheduleDate = parsedDate,
                            adminId = if (adminText.isNotEmpty()) adminText else existingSchedule.adminId
                        )
                        scheduleLists[index] = updatedList

                        val dummyIndex = DummyRequests.scheduledRequestLists.indexOfFirst { it.id == existingSchedule.id }
                        if (dummyIndex != -1) {
                            DummyRequests.scheduledRequestLists[dummyIndex] = updatedList
                        }

                        Toast.makeText(context, "Request List Updated", Toast.LENGTH_SHORT).show()
                    }
                }
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, "Please provide at least an Area, Owner, or Group", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }
}