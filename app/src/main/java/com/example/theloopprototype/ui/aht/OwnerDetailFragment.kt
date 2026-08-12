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

        val tvOwnerInfo = view.findViewById<TextView>(R.id.tvOwnerInfo)
        val tvPetsHistoryInfo = view.findViewById<TextView>(R.id.tvPetsHistoryInfo)
        val btnCreateVisitEntry = view.findViewById<Button>(R.id.btnCreateVisitEntry)
        val btnUpdateOwnerPet = view.findViewById<Button>(R.id.btnUpdateOwnerPet)

        tvOwnerInfo.text = "Owner: ${owner?.firstName} ${owner?.lastName}\nPhone: ${owner?.cellphoneNumber}\nAddress: ${owner?.physicalAddress ?: "N/A"}"

        val historyBuilder = StringBuilder()
        if (linkedPets.isEmpty()) {
            historyBuilder.append("No linked pets found for this owner.")
        } else {
            for (pet in linkedPets) {
                historyBuilder.append("• Pet Name: ${pet.name} (${pet.breed})\n")
                historyBuilder.append("  Sex: ${pet.sex} | Sterilised: ${pet.isSterilised}\n")
                historyBuilder.append("  Weight: ${pet.weightKg} kg | Height: ${pet.heightCm} cm\n")
                historyBuilder.append("  Clinical History: No prior visits recorded.\n\n")
            }
        }

        tvPetsHistoryInfo.text = historyBuilder.toString().trimEnd()

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