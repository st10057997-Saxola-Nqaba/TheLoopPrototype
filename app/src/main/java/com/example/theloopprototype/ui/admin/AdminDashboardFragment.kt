package com.example.theloopprototype.ui.admin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R
import com.example.theloopprototype.data.DummyRequests
import com.example.theloopprototype.models.RequestStatus

class AdminDashboardFragment : Fragment(R.layout.fragment_admin_dashboard) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Navigate to the Pending & Expired Requests view
        view.findViewById<Button>(R.id.btnManageRequests).setOnClickListener {
            findNavController().navigate(R.id.action_adminDashboard_to_adminRequests)
        }

        // Navigate to Scheduling & Notifications Manager
        view.findViewById<Button>(R.id.btnManageScheduling).setOnClickListener {
            findNavController().navigate(R.id.adminSchedulesFragment)
        }

        view.findViewById<Button>(R.id.btnOutcomesSummary).setOnClickListener {
            findNavController().navigate(R.id.action_adminDashboard_to_adminOutreachOutcomes)
        }

        // Correctly route to Admin Profile instead of the AHT edit profile screen
        view.findViewById<Button>(R.id.btnAdminProfile).setOnClickListener {
            findNavController().navigate(R.id.action_adminDashboard_to_adminProfileFragment)
        }

        refreshDashboardStats(view)
    }

    //Refresh counts when returning to this screen , since they can be changed elsewhere
    override fun onResume() {
        super.onResume()
        view?.let {refreshDashboardStats(it)}
    }


    private fun refreshDashboardStats(view: View){
        val pending = DummyRequests.requests.count{ it.status == RequestStatus.PENDING }
        val scheduled = DummyRequests.requests.count{ it.status == RequestStatus.SCHEDULED }
        val fulfilled = DummyRequests.requests.count{ it.status == RequestStatus.FULFILLED }


        view.findViewById<TextView>(R.id.tvPendingRequests).text = pending.toString()
        view.findViewById<TextView>(R.id.tvScheduledRequests).text = scheduled.toString()
        view.findViewById<TextView>(R.id.tvFulfilledRequests).text = fulfilled.toString()

    }
}