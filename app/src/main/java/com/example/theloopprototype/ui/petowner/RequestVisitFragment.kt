package com.example.theloopprototype.ui.petowner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.DummyData
import com.example.theloopprototype.R
import com.example.theloopprototype.data.DummyRequests
import com.example.theloopprototype.models.DPet
import com.example.theloopprototype.models.RequestStatus
import com.example.theloopprototype.models.DRequest
import com.example.theloopprototype.models.Severity
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import java.time.LocalDateTime
import java.util.UUID

class RequestVisitFragment : Fragment() {

    private lateinit var btnBack: ImageButton
    private lateinit var tvPetName: TextView
    private lateinit var tvPetBreed: TextView
    private lateinit var etDescription: EditText
    private lateinit var spinnerSeverity: MaterialAutoCompleteTextView
    private lateinit var btnSubmitRequest: Button

    private val currentOwnerId = "u1"

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
        setupClickListeners(pet)
    }

    private fun setupUI(pet: DPet) {
        tvPetName.text = pet.name
        tvPetBreed.text = "${pet.breed} • ${pet.sex}"

        // Setup severity spinner
        val severityOptions = listOf("LOW", "MEDIUM", "HIGH")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, severityOptions)
        spinnerSeverity.setAdapter(adapter)
        spinnerSeverity.setText(severityOptions[0], false) // default to LOW
    }

    private fun setupClickListeners(pet: DPet) {
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnSubmitRequest.setOnClickListener {
            val description = etDescription.text.toString().trim()
            val severityText = spinnerSeverity.text.toString().ifBlank { "LOW" }
            val severity = Severity.valueOf(severityText)

            if (description.isEmpty()) {
                etDescription.error = "Please describe the issue"
                return@setOnClickListener
            }

            val areaId = DummyData.getRequestsForOwner(currentOwnerId).firstOrNull()?.areaId
                ?: DummyData.areas.firstOrNull()?.id ?: "area1"

            val newRequest = DRequest(
                id = UUID.randomUUID().toString(),
                ownerId = currentOwnerId,
                petId = pet.id,
                areaId = areaId,
                severity = severity,
                description = description,
                status = RequestStatus.PENDING,
                latitude = null,
                longitude = null,
                generatedFromVisitEntryId = null,
                createdAt = LocalDateTime.now(),
                expirationDateTime = LocalDateTime.now().plusDays(7)

            )

            DummyRequests.requests.add(newRequest)

            // In real app, save request to database
            // For prototype, just navigate back
            findNavController().popBackStack(R.id.petOwnerHomeFragment , false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}