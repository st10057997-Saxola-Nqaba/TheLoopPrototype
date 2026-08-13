package com.example.theloopprototype.ui.petowner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.DummyData
import com.example.theloopprototype.R
import com.example.theloopprototype.models.DPet
import java.time.LocalDate
import java.util.UUID

class RequestVisitFragment : Fragment() {

    private lateinit var btnBack: ImageButton
    private lateinit var tvPetName: TextView
    private lateinit var tvPetBreed: TextView
    private lateinit var etDescription: EditText
    private lateinit var spinnerSeverity: Spinner
    private lateinit var btnSubmitRequest: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_request_visit, container, false)

        btnBack = view.findViewById(R.id.btnBack)
        tvPetName = view.findViewById(R.id.tvPetName)
        tvPetBreed = view.findViewById(R.id.tvPetBreed)
        etDescription = view.findViewById(R.id.etDescription)
        spinnerSeverity = view.findViewById(R.id.spinnerSeverity)
        btnSubmitRequest = view.findViewById(R.id.btnSubmitRequest)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val petId = arguments?.getString("petId") ?: ""
        val pet = DummyData.getPetById(petId)
        if (pet == null) {
            findNavController().popBackStack()
            return
        }

        setupUI(pet)
        setupClickListeners()
    }

    private fun setupUI(pet: DPet) {
        tvPetName.text = pet.name
        tvPetBreed.text = "${pet.breed} • ${pet.sex}"

        // Setup severity spinner
        val severityOptions = listOf("LOW", "MEDIUM", "HIGH")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, severityOptions)
        spinnerSeverity.adapter = adapter
    }

    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnSubmitRequest.setOnClickListener {
            val description = etDescription.text.toString().trim()
            val severity = spinnerSeverity.selectedItem.toString()

            if (description.isEmpty()) {
                etDescription.error = "Please describe the issue"
                return@setOnClickListener
            }

            // In real app, save request to database
            // For prototype, just navigate back
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}