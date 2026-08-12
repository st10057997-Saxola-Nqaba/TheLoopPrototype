package com.example.theloopprototype.ui.admin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R

class AdminDashboardFragment : Fragment(R.layout.fragment_admin_dashboard) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnAdminLogout).setOnClickListener {
            Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.loginFragment)
        }

        // Navigate to the Pending & Expired Requests view
        view.findViewById<Button>(R.id.btnManageRequests).setOnClickListener {
            findNavController().navigate(R.id.action_adminDashboard_to_adminRequests)
        }

        view.findViewById<Button>(R.id.btnManageScheduling).setOnClickListener {
            Toast.makeText(requireContext(), "Opening Schedule & Notifications manager", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<Button>(R.id.btnOutcomesSummary).setOnClickListener {
            Toast.makeText(requireContext(), "Opening Outreach Outcomes summary", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<Button>(R.id.btnAdminProfile).setOnClickListener {
            findNavController().navigate(R.id.editProfileFragment)
        }
    }
}