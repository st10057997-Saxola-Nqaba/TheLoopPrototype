package com.example.theloopprototype.ui.admin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R

class AdminEditProfileFragment : Fragment(R.layout.fragment_admin_edit_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            findNavController().popBackStack()
        }

        view.findViewById<Button>(R.id.btnSaveProfile).setOnClickListener {
            Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }
}