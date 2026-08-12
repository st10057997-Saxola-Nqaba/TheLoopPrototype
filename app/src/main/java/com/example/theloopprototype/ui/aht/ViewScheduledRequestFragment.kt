package com.example.theloopprototype.ui.aht

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R
import com.example.theloopprototype.data.*

class ViewScheduledRequestFragment : Fragment(R.layout.fragment_view_scheduled_request) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Dynamically retrieve the passed argument instead of hardcoding "srl1"
        val targetListId = arguments?.getString("targetListId") ?: "srl1"

        // Example data lookup based on passed ID
        val scheduleList = DummyRequests.scheduledRequestLists.find { it.id == targetListId }
        val listItem = DummyRequests.requestListItems.find { it.scheduleRequestListId == targetListId }
        val request = DummyRequests.requests.find { it.id == listItem?.requestId }
        val owner = DummyUsers.users.find { it.id == request?.ownerId }
        val pet = DummyPets.pets.find { it.id == request?.petId }

        // Bind data to views
        view.findViewById<TextView>(R.id.tvOwnerName)?.text = "Owner: ${owner?.firstName} ${owner?.lastName}"
        view.findViewById<TextView>(R.id.tvPetInfo)?.text = "Pet: ${pet?.name} (${pet?.breed})"
        view.findViewById<TextView>(R.id.tvRequestDescription)?.text = "Issue: ${request?.description}"
        view.findViewById<TextView>(R.id.tvScheduleDate)?.text = "Scheduled For: ${scheduleList?.scheduleDate}"
        view.findViewById<TextView>(R.id.tvOwnerContact)?.text = "Phone: ${owner?.cellphoneNumber}"

        // Setup button to navigate to the Create Visit Entry screen
        val btnCreateVisit = view.findViewById<Button>(R.id.btnCreateVisit)
        btnCreateVisit?.setOnClickListener {
            val bundle = Bundle().apply {
                putString("targetListId", targetListId)
            }
            findNavController().navigate(R.id.action_viewScheduledRequestFragment_to_createVisitEntryFragment, bundle)
        }

        // Setup button to update owner details directly from the request screen
        val btnUpdateDetails = view.findViewById<Button>(R.id.btnUpdateOwnerDetails)
        btnUpdateDetails?.setOnClickListener {
            val ownerId = owner?.id ?: return@setOnClickListener
            val bundle = Bundle().apply {
                putString("ownerId", ownerId)
            }
            findNavController().navigate(R.id.action_viewScheduledRequestFragment_to_updateOwnerPetFragment, bundle)
        }
    }
}