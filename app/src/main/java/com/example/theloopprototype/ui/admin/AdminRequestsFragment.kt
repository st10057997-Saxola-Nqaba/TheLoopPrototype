package com.example.theloopprototype.ui.admin

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R
import com.example.theloopprototype.data.DummyRequests
import com.example.theloopprototype.models.DScheduledRequestList
import com.example.theloopprototype.models.RequestStatus
import com.example.theloopprototype.models.ScheduleStatus
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AdminRequestsFragment : Fragment(R.layout.fragment_admin_requests) {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val containerList = view.findViewById<LinearLayout>(R.id.containerRequestsList)
        val btnViewMap = view.findViewById<Button>(R.id.btnViewMap)
        val btnCreateSchedule = view.findViewById<Button>(R.id.btnCreateSchedule)
        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)
        val btnViewExpiredMap = view.findViewById<Button>(R.id.btnViewExpiredMap)

        refreshRequestsList(containerList)
        refreshStats(view)

        // Navigate to full-screen Pending Cluster/Map Screen
        btnViewMap.setOnClickListener {
            findNavController().navigate(R.id.adminMapPickerFragment)
        }

        // Navigate to the separate Expired Requests Map screen
        btnViewExpiredMap?.setOnClickListener {
            findNavController().navigate(R.id.adminExpiredMapFragment)
        }

        // Opens the exact same creation form as AdminSchedulesFragment
        btnCreateSchedule.setOnClickListener {
            showCreateScheduleDialog()
        }

        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    // Code Attribution
//
// Fix: Resolved NullPointerException in AdminRequestsFragment caused by
// incorrect view ID references in stat widgets.
// - Removed references to R.id.tvPendingRequests, R.id.tvScheduledCount, etc.
//   (these IDs only exist in fragment_admin_dashboard.xml)
// - Updated to use the correct IDs from this fragment's own layout:
//   tvPendingCount, tvScheduledCount, tvExpiredCount (fragment_admin_requests.xml)
//
// References:
// - Birch, J. (2019). Exploring View Binding on Android. Google Developer Experts.
//   https://medium.com/google-developer-experts/exploring-view-binding-on-android-44e57ba11635
// - Joshi, S. (n.d.). How to Find, Prevent and Solve NullPointerException in Mobile Apps.
//   DEV Community. https://dev.to/shubham_joshi_expert/how-to-find-prevent-and-solve-javalangnullpointerexception-in-mobile-apps-4304
    override fun onResume(){
        super.onResume()
        view?.let {
            refreshRequestsList(it.findViewById(R.id.containerRequestsList))
            refreshStats(it)
        }
    }

    private fun refreshStats(view: View){
        val pending = DummyRequests.requests.count{ it.status == RequestStatus.PENDING }
        val scheduled = DummyRequests.requests.count{ it.status == RequestStatus.SCHEDULED }
        val fulfilled = DummyRequests.requests.count{ it.status == RequestStatus.EXPIRED }


        view.findViewById<TextView>(R.id.tvPendingCount).text = pending.toString()
        view.findViewById<TextView>(R.id.tvScheduledCount).text = scheduled.toString()
        view.findViewById<TextView>(R.id.tvExpiredCount).text = fulfilled.toString()

    }



    private fun refreshRequestsList(container: LinearLayout) {
        container.removeAllViews()

        val allRequests = DummyRequests.requests
        val pendingList = allRequests.filter { it.status == RequestStatus.PENDING }
        val expiredList = allRequests.filter { it.status == RequestStatus.EXPIRED }

        // Pending Section Header
        val pendingHeader = TextView(requireContext()).apply {
            text = "PENDING REQUESTS (${pendingList.size})"
            setTypeface(null, Typeface.BOLD)
            setPadding(0, 4, 0, 8)
            setTextColor(resources.getColor(R.color.said_navy_text, null))
        }
        container.addView(pendingHeader)

        pendingList.forEach { req ->
            val formattedDate = req.createdAt.format(formatter)
            val card = createRequestCard(
                title = "Area: ${req.areaId} | Pet: ${req.petId ?: "Stray"}",
                desc = req.description,
                meta = "Status: ${req.status} | Date & Time: $formattedDate | Severity: ${req.severity}",
                severity = req.severity.name,
                isExpired = false
            ) {
                showRequestDetailsDialog(
                    req.id,
                    req.areaId,
                    req.petId ?: "None",
                    req.description,
                    req.status.name,
                    "Logged on: $formattedDate"
                )
            }
            container.addView(card)
        }

        // Expired Section Header
        val expiredHeader = TextView(requireContext()).apply {
            text = "\nEXPIRED / STALE REQUESTS (${expiredList.size})"
            setTypeface(null, Typeface.BOLD)
            setPadding(0, 12, 0, 8)
            setTextColor(Color.RED)
        }
        container.addView(expiredHeader)

        expiredList.forEach { req ->
            val formattedDate = req.createdAt.format(formatter)
            val card = createRequestCard(
                title = "Expired - Area: ${req.areaId} | Pet: ${req.petId ?: "Stray"}",
                meta = "Status: Expired Window Elapsed | Logged: $formattedDate",
                desc = req.description,
                severity = req.severity.name,
                isExpired = true
            ) {
                showExpiredActionDialog(req.id, req.areaId, req.petId ?: "None")
            }
            container.addView(card)
        }
    }

    private fun createRequestCard(title: String, desc: String, meta: String, severity: String, isExpired: Boolean, onClick: () -> Unit): View {
        val card = layoutInflater.inflate(R.layout.item_request_card, null, false) as CardView
        card.findViewById<TextView>(R.id.tvRequestTitle).text = title
        card.findViewById<TextView>(R.id.tvRequestDescription).text = desc
        card.findViewById<TextView>(R.id.tvRequestMeta).text = meta

        val badge = card.findViewById<TextView>(R.id.tvSeverityBadge)
        badge.text = severity
        if (isExpired) {
            badge.setBackgroundColor(Color.LTGRAY)
            badge.setTextColor(Color.DKGRAY)
        }

        card.setOnClickListener { onClick() }
        return card
    }

    private fun showCreateScheduleDialog() {
        val context = requireContext()
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Create Request List")

        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 40, 50, 10)
        }

        val inputArea = EditText(context).apply {
            hint = "Area ID (e.g. area2)"
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
            setText(LocalDateTime.now().plusDays(3).format(formatter))
        }
        layout.addView(inputDate)

        val inputAdmin = EditText(context).apply {
            hint = "Assigned AHT / Admin ID (e.g. u8)"
            setText("u8")
        }
        layout.addView(inputAdmin)

        builder.setView(layout)

        builder.setPositiveButton("Save") { _, _ ->
            val areaText = inputArea.text.toString().trim()
            val groupText = inputGroup.text.toString().trim()
            val dateText = inputDate.text.toString().trim()
            val adminText = inputAdmin.text.toString().trim()

            if (areaText.isNotEmpty() || groupText.isNotEmpty()) {
                val parsedDate = try {
                    LocalDateTime.parse(dateText, formatter)
                } catch (e: Exception) {
                    LocalDateTime.now().plusDays(3)
                }

                val effectiveArea = if (areaText.isNotEmpty()) areaText else groupText
                val newId = "srl_${System.currentTimeMillis()}"

                val newList = DScheduledRequestList(
                    id = newId,
                    areaId = effectiveArea,
                    adminId = if (adminText.isNotEmpty()) adminText else "u8",
                    scheduleDate = parsedDate,
                    status = ScheduleStatus.CONFIRMED
                )

                DummyRequests.scheduledRequestLists.add(newList)

                val movedCount = DummyRequests.linkPendingRequestsToSchedule(effectiveArea, newId)

                Toast.makeText(context, "Schedule list created $movedCount pending request in $effectiveArea moved to scheduled", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.adminSchedulesFragment)
            } else {
                Toast.makeText(context, "Please provide at least an Area or Group", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    private fun showRequestDetailsDialog(id: String, area: String, pet: String, issue: String, status: String, notes: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Request Details ($id)")
            .setMessage("Area: $area\nPet ID: $pet\nIssue: $issue\nStatus: $status\n\nNotes: $notes")
            .setPositiveButton("Close", null)
            .show()
    }

    private fun showExpiredActionDialog(id: String, area: String, pet: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Expired Request Options ($id)")
            .setMessage("This request for Pet $pet in $area elapsed before unit dispatch.\n\nChoose an administrative action:")
            .setPositiveButton("Follow-up / Referral") { _, _ ->
                Toast.makeText(requireContext(), "Referral alternative sent to owner.", Toast.LENGTH_SHORT).show()
            }
            .setNeutralButton("Priority Next Cycle") { _, _ ->
                Toast.makeText(requireContext(), "Queued for priority inclusion in next cycle.", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}