package com.example.theloopprototype.ui.aht

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R
import com.example.theloopprototype.data.DummyPets
import com.example.theloopprototype.data.DummyUsers

class OwnerDetailFragment : Fragment(R.layout.fragment_owner_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ownerId = arguments?.getString("ownerId") ?: return

        val owner = DummyUsers.users.find { it.id == ownerId }
        val linkedPets = DummyPets.pets.filter { it.ownerId == ownerId }

        val tvOwnerName = view.findViewById<TextView>(R.id.tvOwnerName)
        val tvOwnerPhone = view.findViewById<TextView>(R.id.tvOwnerPhone)
        val tvOwnerEmail = view.findViewById<TextView>(R.id.tvOwnerEmail)
        val tvOwnerAddress = view.findViewById<TextView>(R.id.tvOwnerAddress)
        val tvNoPets = view.findViewById<TextView>(R.id.tvNoPets)

        val btnCreateVisitEntry = view.findViewById<Button>(R.id.btnCreateVisitEntry)
        val btnUpdateOwnerPet = view.findViewById<Button>(R.id.btnUpdateOwnerPet)

        tvOwnerName.text = "${owner?.firstName} ${owner?.lastName}"
        tvOwnerPhone.text = owner?.cellphoneNumber
        tvOwnerEmail.text = owner?.emailAddress ?: "N/A"
        tvOwnerAddress.text = owner?.physicalAddress ?: "N/A"

        // Handle Pets visibility and data
        if (linkedPets.isEmpty()) {
            tvNoPets.visibility = View.VISIBLE
            view.findViewById<View>(R.id.layoutPet1).visibility = View.GONE
            view.findViewById<View>(R.id.dividerPet1).visibility = View.GONE
            view.findViewById<View>(R.id.layoutPet2).visibility = View.GONE
            view.findViewById<View>(R.id.dividerPet2).visibility = View.GONE
            view.findViewById<View>(R.id.layoutPet3).visibility = View.GONE
        } else {
            tvNoPets.visibility = View.GONE
            
            // Pet 1
            if (linkedPets.size >= 1) {
                val pet = linkedPets[0]
                view.findViewById<View>(R.id.layoutPet1).visibility = View.VISIBLE
                view.findViewById<TextView>(R.id.tvPet1Name).text = "🐾 ${pet.name}"
                view.findViewById<TextView>(R.id.tvPet1Details).text = "${pet.breed} • ${pet.sex} • ${pet.weightKg}kg"
                view.findViewById<TextView>(R.id.tvPet1History).text = "History: No prior visits recorded."
            } else {
                view.findViewById<View>(R.id.layoutPet1).visibility = View.GONE
            }

            // Divider 1
            view.findViewById<View>(R.id.dividerPet1).visibility = if (linkedPets.size > 1) View.VISIBLE else View.GONE

            // Pet 2
            if (linkedPets.size >= 2) {
                val pet = linkedPets[1]
                view.findViewById<View>(R.id.layoutPet2).visibility = View.VISIBLE
                view.findViewById<TextView>(R.id.tvPet2Name).text = "🐾 ${pet.name}"
                view.findViewById<TextView>(R.id.tvPet2Details).text = "${pet.breed} • ${pet.sex} • ${pet.weightKg}kg"
                view.findViewById<TextView>(R.id.tvPet2History).text = "History: No prior visits recorded."
            } else {
                view.findViewById<View>(R.id.layoutPet2).visibility = View.GONE
            }

            // Divider 2
            view.findViewById<View>(R.id.dividerPet2).visibility = if (linkedPets.size > 2) View.VISIBLE else View.GONE

            // Pet 3
            if (linkedPets.size >= 3) {
                val pet = linkedPets[2]
                view.findViewById<View>(R.id.layoutPet3).visibility = View.VISIBLE
                view.findViewById<TextView>(R.id.tvPet3Name).text = "🐾 ${pet.name}"
                view.findViewById<TextView>(R.id.tvPet3Details).text = "${pet.breed} • ${pet.sex} • ${pet.weightKg}kg"
                view.findViewById<TextView>(R.id.tvPet3History).text = "History: No prior visits recorded."
            } else {
                view.findViewById<View>(R.id.layoutPet3).visibility = View.GONE
            }
        }

        btnCreateVisitEntry.setOnClickListener {
            val bundle = Bundle().apply {
                putString("ownerId", ownerId)
            }
            findNavController().navigate(R.id.action_ownerDetailFragment_to_createVisitEntryFragment, bundle)
        }

        btnUpdateOwnerPet.setOnClickListener {
            val bundle = Bundle().apply {
                putString("ownerId", ownerId)
            }
            findNavController().navigate(R.id.action_ownerDetailFragment_to_updateOwnerPetFragment, bundle)
        }
    }
}
