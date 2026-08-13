package com.example.theloopprototype.ui.petowner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.theloopprototype.DummyData
import com.example.theloopprototype.R
import com.example.theloopprototype.adapter.RequestAdapter
import com.example.theloopprototype.databinding.FragmentMyRequestsBinding
import com.example.theloopprototype.models.DRequest
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

        binding.tvRequestCount.text = "You have ${requests.size} request(s)"
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnNewRequest.setOnClickListener {
           showPetPickerAndRequest()

        }
    }

    private fun showPetPickerAndRequest(){
        val pets = DummyData.getPetsForOwner(currentOwnerId)

        if (pets.isEmpty()){
            Toast.makeText(
                requireContext(),
                "Add a pet first before requesting a visit.",
                Toast.LENGTH_LONG

            ).show()

            return
        }

        val petNames = pets.map {it.name}.toTypedArray()
        AlertDialog.Builder(requireContext())
            .setTitle("Which pet is this request for?")
            .setItems(petNames) { _,index ->
                val bundle = Bundle().apply{
                    putString("petId",pets[index].id)
                }
                findNavController().navigate(R.id.requestVisitFragment,bundle)

            }
            .setNegativeButton("Cancel",null)
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
