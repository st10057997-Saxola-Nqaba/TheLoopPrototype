// Code Attribution
// This method was taken from Stack Overflow
// https://stackoverflow.com/questions/41409805/how-to-pass-data-from-one-fragment-to-another-in-android
// Ankit Sinha
// https://stackoverflow.com/users/2223529/ankit-sinha
package com.example.theloopprototype.ui.petowner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theloopprototype.DummyData
import com.example.theloopprototype.R
import com.example.theloopprototype.adapter.RequestAdapter
import com.example.theloopprototype.databinding.FragmentMyRequestsBinding
import com.example.theloopprototype.models.DRequest
import com.example.theloopprototype.models.RequestStatus
import android.app.AlertDialog

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

        // Matches the two stat tiles in the new fragment_my_requests.xml
        binding.tvTotalRequestCount.text = requests.size.toString()
        binding.tvPendingRequestCount.text =
            requests.count { it.status == RequestStatus.PENDING }.toString()
    }

    private fun setupClickListeners() {
        // No back button on this screen anymore — it's a top-level bottom-nav tab now,
        // not a screen you navigate "back" from.

        binding.btnNewRequest.setOnClickListener {
            showPetPickerAndRequest()
        }
    }

    private fun showPetPickerAndRequest() {
        val pets = DummyData.getPetsForOwner(currentOwnerId)

        if (pets.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Add a pet first before requesting a visit.",
                Toast.LENGTH_LONG
            ).show()
            return
        }

        val petNames = pets.map { it.name }.toTypedArray()
        AlertDialog.Builder(requireContext())
            .setTitle("Which pet is this request for?")
            .setItems(petNames) { _, index ->
                val bundle = Bundle().apply {
                    putString("petId", pets[index].id)
                }
                findNavController().navigate(R.id.requestVisitFragment, bundle)
            }
            .setNegativeButton("Cancel", null)
            .show()
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