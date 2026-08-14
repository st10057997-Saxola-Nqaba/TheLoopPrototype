package com.example.theloopprototype.ui.admin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R
import com.example.theloopprototype.data.DummyRequests

class AdminVisitEntryDetailFragment : Fragment(R.layout.fragment_admin_visit_entry_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val visitId = arguments?.getString("visitId") ?: "v1"

        view.findViewById<Button>(R.id.btnBackFromVisitDetail).setOnClickListener {
            findNavController().popBackStack()
        }

        // Find visit entry matching the ID across mock data
        val visit = DummyRequests.outreachOutcomes
            .flatMap { it.visitSummaries }
            .firstOrNull { it.visitId == visitId }

        visit?.let {
            view.findViewById<TextView>(R.id.textDetailVisitId).text = "Visit ID: ${it.visitId}"
            view.findViewById<TextView>(R.id.textDetailOwner).text = "Pet Owner: ${it.petOwnerName}"
            view.findViewById<TextView>(R.id.textDetailOutcome).text = "Outcome: ${it.visitOutcome}"
            view.findViewById<TextView>(R.id.textDetailFlags).text = "Flags Posted: ${it.flagsPosted}"
            view.findViewById<TextView>(R.id.textDetailOfficer).text = "AHT Officer: ${it.officerId}"
            view.findViewById<TextView>(R.id.textDetailTimestamp).text = "Timestamp: ${it.timestamp}"
            view.findViewById<TextView>(R.id.textDetailNotes).text = "Operational Notes:\n${it.detailedNotes}"
        }
    }
}