package com.example.theloopprototype.ui.petowner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.theloopprototype.DummyData
import com.example.theloopprototype.R
import com.example.theloopprototype.adapter.PetAdapter
import com.example.theloopprototype.models.DPet

class PetOwnerHomeFragment : Fragment() {

    private lateinit var tvPetCount: TextView
    private lateinit var tvRequestCount: TextView
    private lateinit var btnAddPet: Button
    private lateinit var rvPets: RecyclerView

    private val currentOwnerId = "u1"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_pet_owner_home, container, false)

        tvPetCount = view.findViewById(R.id.tvPetCount)
        tvRequestCount = view.findViewById(R.id.tvRequestCount)
        btnAddPet = view.findViewById(R.id.btnAddPet)
        rvPets = view.findViewById(R.id.rvPets)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        updateStats()
        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        //refreshes every time we come back to home
        setupRecyclerView()
        updateStats()
    }

    private fun setupRecyclerView() {
        val pets = DummyData.getPetsForOwner(currentOwnerId)
        val adapter = PetAdapter(pets) { pet ->
            navigateToViewPet(pet)
        }

        rvPets.layoutManager = LinearLayoutManager(context)
        rvPets.adapter = adapter
    }

    private fun updateStats() {
        val pets = DummyData.getPetsForOwner(currentOwnerId)
        val requests = DummyData.getRequestsForOwner(currentOwnerId)
        tvPetCount.text = pets.size.toString()
        tvRequestCount.text = requests.size.toString()
    }

    private fun setupClickListeners() {
        btnAddPet.setOnClickListener {
            findNavController().navigate(R.id.action_petOwnerHomeFragment_to_addPetFragment)
        }
        // Requests and Profile navigation is now handled by the BottomNavigationView
        // in MainActivity — no click listeners needed here for those anymore.
    }

    private fun navigateToViewPet(pet: DPet) {
        val bundle = Bundle().apply {
            putString("petId", pet.id)
        }
        findNavController().navigate(R.id.action_petOwnerHomeFragment_to_viewPetFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}