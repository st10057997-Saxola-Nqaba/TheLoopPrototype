package com.example.theloopprototype.ui.aht

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R

class CreateVisitEntryFragment : Fragment(R.layout.fragment_create_visit_entry) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etCareGiven = view.findViewById<EditText>(R.id.etCareGiven)
        val etWelfareConcern = view.findViewById<EditText>(R.id.etWelfareConcern)
        val cbReturnVisit = view.findViewById<CheckBox>(R.id.cbReturnVisit)
        val btnSaveVisit = view.findViewById<Button>(R.id.btnSaveVisit)

        // Retrieve passed arguments (like schedule or pet ID) if needed
        val targetListId = arguments?.getString("targetListId")

        btnSaveVisit.setOnClickListener {
            val careGiven = etCareGiven.text.toString()
            val concern = etWelfareConcern.text.toString()
            val needsReturn = cbReturnVisit.isChecked

            if (careGiven.isBlank()) {
                Toast.makeText(requireContext(), "Please log the care given", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // TODO: Save visit data to DummyData or repository here

            Toast.makeText(requireContext(), "Visit Entry Saved Successfully!", Toast.LENGTH_SHORT).show()

            // Navigate back or to home
            findNavController().popBackStack()
        }
    }
}