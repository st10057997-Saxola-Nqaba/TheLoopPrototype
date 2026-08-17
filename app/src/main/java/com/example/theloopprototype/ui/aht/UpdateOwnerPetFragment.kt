package com.example.theloopprototype.ui.aht

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R
import com.example.theloopprototype.data.DummyPets
import com.example.theloopprototype.data.DummyUsers
import com.google.android.material.appbar.MaterialToolbar

class UpdateOwnerPetFragment : Fragment(R.layout.fragment_update_owner_pet) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<MaterialToolbar>(R.id.btnBack)?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val ownerId = arguments?.getString("ownerId") ?: return
        val ownerIndex = DummyUsers.users.indexOfFirst { it.id == ownerId }
        val petIndex = DummyPets.pets.indexOfFirst { it.ownerId == ownerId }

        val owner = if (ownerIndex != -1) DummyUsers.users[ownerIndex] else null
        val pet = if (petIndex != -1) DummyPets.pets[petIndex] else null

        val etPhone = view.findViewById<EditText>(R.id.etUpdatePhone)
        val etAddress = view.findViewById<EditText>(R.id.etUpdateAddress)
        val etWeight = view.findViewById<EditText>(R.id.etUpdateWeight)
        val etHeight = view.findViewById<EditText>(R.id.etUpdateHeight)
        val btnSave = view.findViewById<Button>(R.id.btnSaveChanges)

        owner?.let {
            etPhone.setText(it.cellphoneNumber)
            etAddress.setText(it.physicalAddress)
        }

        pet?.let {
            etWeight.setText(it.weightKg.toString())
            etHeight.setText(it.heightCm.toString())
        }

        btnSave.setOnClickListener {
            val newPhone = etPhone.text.toString().trim()
            val newAddress = etAddress.text.toString().trim()
            val newWeight = etWeight.text.toString().toDoubleOrNull() ?: 0.0
            val newHeight = etHeight.text.toString().toDoubleOrNull() ?: 0.0

            if (owner != null && ownerIndex != -1) {
                DummyUsers.users[ownerIndex] = owner.copy(
                    cellphoneNumber = newPhone,
                    physicalAddress = newAddress
                )
            }

            if (pet != null && petIndex != -1) {
                DummyPets.pets[petIndex] = pet.copy(
                    weightKg = newWeight,
                    heightCm = newHeight
                )
            }

            Toast.makeText(requireContext(), "Owner and Pet data updated successfully!", Toast.LENGTH_SHORT).show()

            val bundle = Bundle().apply {
                putString("ownerId", ownerId)
            }
            findNavController().navigate(R.id.action_updateOwnerPetFragment_to_ownerDetailFragment, bundle)
        }
    }
}