package com.example.theloopprototype.ui.aht

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R

class MyProfileFragment : Fragment(R.layout.fragment_my_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//had to edit here so fragment_my_profile could work, to use correct iDs
        val tvProfileName = view.findViewById<TextView>(R.id.tvProfileName)
        val tvProfileEmail = view.findViewById<TextView>(R.id.tvProfileEmail)
        val tvProfileRole = view.findViewById<TextView>(R.id.tvProfileRole)
        val btnEditProfile = view.findViewById<Button>(R.id.btnEditProfile)
        val btnDeleteProfile = view.findViewById<Button>(R.id.btnDeleteProfile)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        tvProfileName.text = "John Doe"
        tvProfileEmail.text = "aht@example.com"
        tvProfileRole.text = "AHT User"

        btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_myProfileFragment_to_editProfileFragment)
        }

        btnDeleteProfile.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Delete Profile")
                .setMessage("Are you sure you want to delete your profile? This action cannot be undone.")
                .setPositiveButton("Delete") { _, _ ->
                    Toast.makeText(requireContext(), "Profile deleted", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.loginFragment)
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        btnLogout.setOnClickListener {
            Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.loginFragment)
        }
    }
}