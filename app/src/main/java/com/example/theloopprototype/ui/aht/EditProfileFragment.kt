package com.example.theloopprototype.ui.aht

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R
import com.google.android.material.appbar.MaterialToolbar

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbarEditProfile)
        toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val etEditName = view.findViewById<EditText>(R.id.etEditName)
        val btnSave = view.findViewById<Button>(R.id.btnSaveProfileChanges)

        etEditName.setText("John Doe")

        btnSave.setOnClickListener {
            Toast.makeText(requireContext(), "Profile & password updated successfully!", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }
}