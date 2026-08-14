package com.example.theloopprototype.ui.admin

import android.os.Bundle
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

        // Dynamically populate outreach outcome cards from Dummy data
        container.removeAllViews()
        for (outcome in DummyRequests.outreachOutcomes) {
            val itemLayout = LinearLayout(requireContext()).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(0, 0, 0, 24)
            }

            val titleView = TextView(requireContext()).apply {
                text = outcome.initiativeName
                textSize = 16f
                setTypeface(null, android.graphics.Typeface.BOLD)
                setTextColor(resources.getColor(R.color.said_navy_text, null))
            }

            val detailsView = TextView(requireContext()).apply {
                text = "Area: ${outcome.areaId}\nPeriod: ${outcome.startDate} to ${outcome.endDate}\nTotal Flags: ${outcome.totalFlags}\nSummary: ${outcome.flagSummary}"
                textSize = 14f
                setPadding(0, 4, 0, 0)
            }

            itemLayout.addView(titleView)
            itemLayout.addView(detailsView)
            container.addView(itemLayout)
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)?.visibility = View.GONE
    }
}