package com.example.theloopprototype.ui.petowner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R
import com.example.theloopprototype.databinding.FragmentAddPetBinding
import com.example.theloopprototype.DummyData
import com.example.theloopprototype.data.DummyPets
import com.example.theloopprototype.models.DPet
import java.time.LocalDate
import java.util.UUID

class AddPetFragment : Fragment() {

    private var _binding: FragmentAddPetBinding? = null
    private val binding get() = _binding!!

    private val currentOwnerId = "u1"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinners()
        setupClickListeners()
    }

    private fun setupSpinners() {
        // Animal Types
        val animalTypes = DummyData.animalTypes
        val typeNames = animalTypes.map { it.typeName }
        val typeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, typeNames)
        binding.spinnerAnimalType.setAdapter(typeAdapter)

        // Gender options
        val genderOptions = listOf("Male", "Female")
        val genderAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, genderOptions)
        binding.spinnerGender.setAdapter(genderAdapter)
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSavePet.setOnClickListener {
            savePet()
        }
    }

    //Code attribution
    //fix for pet not savind due to Val/Mutablelist misunderstanding
    //kotlin documentation(n.d.) Collections overview
    //https://kotlinlang.org/docs/collections-overview.html
    private fun savePet() {
        val name = binding.etPetName.text.toString().trim()
        val animalType = binding.spinnerAnimalType.text.toString()
        val breed = binding.etBreed.text.toString().trim()
        val gender = binding.spinnerGender.text.toString()
        val dateOfBirth = binding.etDateOfBirth.text.toString().trim()
        val weight = binding.etWeight.text.toString().toDoubleOrNull() ?: 0.0
        val height = binding.etHeight.text.toString().toDoubleOrNull() ?: 0.0
        val sterilised = binding.chkSterilised.isChecked

        if (name.isEmpty() || breed.isEmpty()) {
            binding.etPetName.error = "Please fill in all required fields"
            return
        }

        // Find animal type ID
        val animalTypeId = DummyData.animalTypes
            .firstOrNull { it.typeName.equals(animalType, ignoreCase = true) }
            ?.id ?: "unknown"

        val pet = DPet(
            id = UUID.randomUUID().toString(),
            ownerId = currentOwnerId, // In real app, this would be the logged-in user
            animalTypeId = animalTypeId,
            name = name,
            breed = breed,
            sex = gender,
            dateOfBirth = if (dateOfBirth.isNotEmpty()) LocalDate.parse(dateOfBirth) else null,
            weightKg = weight,
            heightCm = height,
            isSterilised = sterilised
        )

        //writes to fake memory
        DummyPets.pets.add(pet)


        // In real app, save to database
        // For prototype, just navigate back
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}