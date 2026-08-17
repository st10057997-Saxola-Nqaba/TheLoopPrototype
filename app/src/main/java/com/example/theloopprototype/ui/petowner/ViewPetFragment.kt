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
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theloopprototype.DummyData
import com.example.theloopprototype.R
import com.example.theloopprototype.adapter.VisitEntryAdapter
import com.example.theloopprototype.databinding.FragmentViewPetBinding
import java.time.format.DateTimeFormatter

class ViewPetFragment : Fragment() {

    private var _binding: FragmentViewPetBinding? = null
    private val binding get() = _binding!!
    private val args: ViewPetFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pet = DummyData.getPetById(args.petId)
        if (pet == null) {
            findNavController().popBackStack()
            return
        }

        displayPetDetails(pet)
        setupVisitHistory(pet.id)
        setupClickListeners(pet.id)
    }

    private fun displayPetDetails(pet: com.example.theloopprototype.models.DPet) {
        val animalType = DummyData.animalTypes.find { it.id == pet.animalTypeId }?.typeName ?: "Unknown"

        binding.tvPetName.text = pet.name
        binding.tvAnimalType.text = "Type: $animalType"
        binding.tvBreed.text = "Breed: ${pet.breed}"
        binding.tvGender.text = "Gender: ${pet.sex}"
        binding.tvDateOfBirth.text = "Date of Birth: ${pet.dateOfBirth?.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) ?: "Unknown"}"
        binding.tvWeight.text = "Weight: ${pet.weightKg} kg"
        binding.tvHeight.text = "Height: ${pet.heightCm} cm"
        binding.tvSterilised.text = if (pet.isSterilised) "✓ Sterilised" else "✗ Not Sterilised"
        binding.tvSterilised.setTextColor(
            if (pet.isSterilised) android.graphics.Color.parseColor("#4CAF50")
            else android.graphics.Color.parseColor("#F44336")
        )
    }

    private fun setupVisitHistory(petId: String) {
        val visitEntries = DummyData.getVisitEntriesForPet(petId)
        val adapter = VisitEntryAdapter(visitEntries)

        binding.rvVisitHistory.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        if (visitEntries.isEmpty()) {
            binding.tvNoHistory.visibility = View.VISIBLE
            binding.rvVisitHistory.visibility = View.GONE
        } else {
            binding.tvNoHistory.visibility = View.GONE
            binding.rvVisitHistory.visibility = View.VISIBLE
        }
    }

    private fun setupClickListeners(petId: String) {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnRequestVisit.setOnClickListener {
            val action = ViewPetFragmentDirections
                .actionViewPetFragmentToRequestVisitFragment(petId)
            findNavController().navigate(action)
        }

        binding.btnViewFullHistory.setOnClickListener {
            val action = ViewPetFragmentDirections
                .actionViewPetFragmentToViewVisitHistoryFragment(petId)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}