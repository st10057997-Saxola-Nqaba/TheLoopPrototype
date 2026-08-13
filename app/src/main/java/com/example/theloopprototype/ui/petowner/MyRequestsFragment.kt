package com.example.theloopprototype.ui.petowner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.theloopprototype.DummyData
import com.example.theloopprototype.R
import com.example.theloopprototype.adapter.RequestAdapter
import com.example.theloopprototype.databinding.FragmentMyRequestsBinding
import com.example.theloopprototype.models.DRequest

class MyRequestsFragment : Fragment() {

    private var _binding: FragmentMyRequestsBinding? = null
    private val binding get() = _binding!!

    private val currentOwnerId = "u1"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyRequestsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        val requests = DummyData.getRequestsForOwner(currentOwnerId)
        val adapter = RequestAdapter(requests) { request ->
            navigateToRequestDetails(request)
        }

        binding.rvRequests.layoutManager = LinearLayoutManager(context)
        binding.rvRequests.adapter = adapter

        binding.tvRequestCount.text = "You have ${requests.size} request(s)"
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnNewRequest.setOnClickListener {
            // Navigate to AddPet fragment - using action ID from nav_graph
            findNavController().navigate(R.id.action_myRequestsFragment_to_addPetFragment)
        }
    }

    private fun navigateToRequestDetails(request: DRequest) {
        val action = MyRequestsFragmentDirections.actionMyRequestsFragmentToRequestDetailsFragment(request.id)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
