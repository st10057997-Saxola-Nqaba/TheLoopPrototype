package com.example.theloopprototype.ui.admin

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R
import com.example.theloopprototype.data.DummyRequests
import com.example.theloopprototype.models.RequestStatus
import java.time.format.DateTimeFormatter

class AdminRequestsFragment : Fragment(R.layout.fragment_admin_requests) {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val containerList = view.findViewById<LinearLayout>(R.id.containerRequestsList)
        val btnViewMap = view.findViewById<Button>(R.id.btnViewMap)
        val btnCreateSchedule = view.findViewById<Button>(R.id.btnCreateSchedule)
        val btnBackToDashboard = view.findViewById<Button>(R.id.btnBackToDashboard)
        val btnViewExpiredMap = view.findViewById<Button>(R.id.btnViewExpiredMap)

        refreshRequestsList(containerList)

        // Navigate to full-screen Pending Cluster/Map Screen
        btnViewMap.setOnClickListener {
            findNavController().navigate(R.id.adminMapPickerFragment)
        }

        // Navigate to the separate Expired Requests Map screen
        btnViewExpiredMap?.setOnClickListener {
            findNavController().navigate(R.id.adminExpiredMapFragment)
        }

        // Schedule area with Drop-Pin map picker dialog
        btnCreateSchedule.setOnClickListener {
            showDropPinMapPicker()
        }

        btnBackToDashboard.setOnClickListener {
            findNavController().popBackStack()
        }
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

    private fun showDropPinMapPicker() {
        AlertDialog.Builder(requireContext())
            .setTitle("Schedule Area (Drop Pin)")
            .setMessage("Select an active sector to drop schedule marker pin:")
            .setPositiveButton("Area 2 (Tembisa) - Drop Pin") { _, _ ->
                Toast.makeText(requireContext(), "Pin dropped at Tembisa Center. Schedule list created!", Toast.LENGTH_LONG).show()
            }
            .setNeutralButton("Area 3 (Ivory Park) - Drop Pin") { _, _ ->
                Toast.makeText(requireContext(), "Pin dropped at Ivory Park Center. Schedule list created!", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
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