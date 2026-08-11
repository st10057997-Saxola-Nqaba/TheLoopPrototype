package com.example.theloopprototype.ui.aht

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.theloopprototype.R
import com.example.theloopprototype.data.*

class ViewScheduledRequestFragment : Fragment(R.layout.fragment_view_scheduled_request) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Example data lookup for scheduled list "srl1"
        val targetListId = "srl1"
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
    }
}