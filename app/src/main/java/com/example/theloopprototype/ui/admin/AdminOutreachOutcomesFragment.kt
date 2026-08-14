package com.example.theloopprototype.ui.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R
import com.example.theloopprototype.data.DummyRequests
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminOutreachOutcomesFragment : Fragment(R.layout.fragment_admin_outreach_outcomes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnBack = view.findViewById<Button>(R.id.btnBackFromOutcomes)
        val container = view.findViewById<LinearLayout>(R.id.containerOutcomesList)

        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        container.removeAllViews()
        val inflater = LayoutInflater.from(requireContext())

        for (outcome in DummyRequests.outreachOutcomes) {
            // Inflate main outreach card
            val cardView = inflater.inflate(R.layout.item_outreach_outcome, container, false)

            val textInitiativeName = cardView.findViewById<TextView>(R.id.textInitiativeName)
            val textAreaId = cardView.findViewById<TextView>(R.id.textAreaId)
            val textPeriodAndFlags = cardView.findViewById<TextView>(R.id.textPeriodAndFlags)
            val textFlagSummary = cardView.findViewById<TextView>(R.id.textFlagSummary)
            val containerVisits = cardView.findViewById<LinearLayout>(R.id.containerVisits)

            textInitiativeName.text = outcome.initiativeName
            textAreaId.text = "Area: ${outcome.areaId}"
            textPeriodAndFlags.text = "Period: ${outcome.startDate} to ${outcome.endDate} | Total Flags: ${outcome.totalFlags}"
            textFlagSummary.text = "Summary: ${outcome.flagSummary}"

            // Populate individual visit summary cards
            containerVisits.removeAllViews()
            for (visit in outcome.visitSummaries) {
                val visitCard = inflater.inflate(R.layout.item_visit_summary, containerVisits, false)

                val textOwnerName = visitCard.findViewById<TextView>(R.id.textOwnerName)
                val textVisitOutcome = visitCard.findViewById<TextView>(R.id.textVisitOutcome)
                val textFlagsPosted = visitCard.findViewById<TextView>(R.id.textFlagsPosted)

                textOwnerName.text = "Owner: ${visit.petOwnerName}"
                textVisitOutcome.text = "Outcome: ${visit.visitOutcome}"
                textFlagsPosted.text = "Flags: ${visit.flagsPosted}"

                // Wire up click listener to pass visitId via navigation action
                visitCard.setOnClickListener {
                    val bundle = Bundle().apply {
                        putString("visitId", visit.visitId)
                    }
                    findNavController().navigate(
                        R.id.action_adminOutreachOutcomesFragment_to_adminVisitEntryDetailFragment,
                        bundle
                    )
                }

                containerVisits.addView(visitCard)
            }

            container.addView(cardView)
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.GONE
    }
}