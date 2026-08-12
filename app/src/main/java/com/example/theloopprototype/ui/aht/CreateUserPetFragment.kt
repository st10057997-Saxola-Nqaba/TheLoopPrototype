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
import com.example.theloopprototype.models.DPet
import com.example.theloopprototype.models.DUser
import com.example.theloopprototype.models.Role
import java.time.LocalDate
import java.util.UUID

class CreateUserPetFragment : Fragment(R.layout.fragment_create_user_pet) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etFirstName = view.findViewById<EditText>(R.id.etFirstName)
        val etLastName = view.findViewById<EditText>(R.id.etLastName)
        val etPhone = view.findViewById<EditText>(R.id.etPhone)
        val etAddress = view.findViewById<EditText>(R.id.etAddress)
        val etPetName = view.findViewById<EditText>(R.id.etPetName)
        val etPetBreed = view.findViewById<EditText>(R.id.etPetBreed)
        val btnSave = view.findViewById<Button>(R.id.btnSaveProfile)

        val initialPhone = arguments?.getString("initialPhone")
        if (!initialPhone.isNullOrBlank()) {
            etPhone.setText(initialPhone)
        }

        btnSave.setOnClickListener {
            val fName = etFirstName.text.toString().trim()
            val lName = etLastName.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val address = etAddress.text.toString().trim()
            val petName = etPetName.text.toString().trim()
            val petBreed = etPetBreed.text.toString().trim()

            // Enforce mandatory fields including address
            if (fName.isBlank() || lName.isBlank() || phone.isBlank() || address.isBlank() || petName.isBlank()) {
                Toast.makeText(requireContext(), "Please fill in all mandatory fields (* including Address)", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val newOwnerId = "u_" + UUID.randomUUID().toString().take(6)
            val newPetId = "p_" + UUID.randomUUID().toString().take(6)

            val newOwner = DUser(
                id = newOwnerId,
                firstName = fName,
                lastName = lName,
                physicalAddress = address,
                cellphoneNumber = phone,
                emailAddress = null,
                role = Role.OWNER
            )

            val newPet = DPet(
                id = newPetId,
                ownerId = newOwnerId,
                animalTypeId = "at1",
                name = petName,
                breed = petBreed,
                sex = "Unknown",
                dateOfBirth = LocalDate.now(),
                weightKg = 0.0,
                heightCm = 0.0,
                isSterilised = false
            )

            DummyUsers.users.add(newOwner)
            DummyPets.pets.add(newPet)

            Toast.makeText(requireContext(), "Profile created successfully!", Toast.LENGTH_SHORT).show()

            val bundle = Bundle().apply {
                putString("ownerId", newOwnerId)
            }
            findNavController().navigate(R.id.action_createUserPetFragment_to_ownerDetailFragment, bundle)
        }
    }
}